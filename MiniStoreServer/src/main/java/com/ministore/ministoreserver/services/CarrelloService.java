package com.ministore.ministoreserver.services;

import com.ministore.ministoreserver.entities.*;
import com.ministore.ministoreserver.repositories.OrdineRepository;
import com.ministore.ministoreserver.repositories.ProdottoCarrelloRepository;
import com.ministore.ministoreserver.repositories.ProdottoOrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class CarrelloService {

    @Autowired
    private ProdottoCarrelloRepository prodottoCarrelloRepository;

    private EntityManager entityManager;

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private ProdottoOrdineRepository prodottoOrdineRepository;

    @Transactional(readOnly = false)
    public void emptyCart() {
        Utente u = utenteService.getUtente();
        List<ProdottoCarrello> car = (List<ProdottoCarrello>) u.getCarrello();
        car.clear();
        entityManager.flush();
    }//emptyCart

    @Transactional(readOnly = false)
    public void removeProdottoCarrello(ProdottoCarrello prodotto) {
        Utente u = utenteService.getUtente();
        prodotto.setUtente(u);

        u.getCarrello().remove(prodotto);
        entityManager.flush();
    }//removeProdottoCarrello

    @Transactional(readOnly = false)
    public ProdottoCarrello addProdotto( ProdottoCarrello prodotto) {
        Utente u = utenteService.getUtente();
        prodotto.setUtente(u);

        for (ProdottoCarrello p : u.getCarrello()) {
            if(p.equals(prodotto)) {
                int newQuant = p.getQuantita() + prodotto.getQuantita();

                if(newQuant>p.getProdotto().getQuantita())
                    throw new IllegalStateException("Non disponibile!");

                p.setQuantita(newQuant);
                return p;
            }//if
        }//for

        prodotto = prodottoCarrelloRepository.save(prodotto);
        return prodotto;
    }//addProdotto


    @Transactional
    public List<ProdottoCarrello> updateCarrello( List<ProdottoCarrello> prodotti) {
        Utente u = utenteService.getUtente();
        u.getCarrello().clear();

        for (ProdottoCarrello p : prodotti) {
            p.setUtente(u);
            p = prodottoCarrelloRepository.save(p);
            u.getCarrello().add(p);
        }//for

        return (List<ProdottoCarrello>) u.getCarrello();
    }//updateCarrello


    @Transactional(readOnly = false)
    public Ordine registraOrdine(String ind) {
        Utente u = utenteService.getUtente();

        if (u.getCarrello().isEmpty())
            throw new IllegalStateException();

        Ordine newOrdine = new Ordine();
        newOrdine.setUtente(u);


        newOrdine.setData(new Date());
        newOrdine.setId(0);
        newOrdine.setIndirizzo(ind);
        newOrdine.setProdottiOrdini(new LinkedList<>());
        newOrdine = ordineRepository.save(newOrdine);
        entityManager.flush();

        //GROWING
        for(ProdottoCarrello p : prodottoCarrelloRepository.findByUtente(u)) entityManager.lock(p.getProdotto(), LockModeType.OPTIMISTIC);

        for (ProdottoCarrello p : prodottoCarrelloRepository.findByUtente(u)) {

            Prodotto prod = entityManager.find(Prodotto.class, p.getProdotto().getId());
            ProdottoOrdine op = new ProdottoOrdine();
            int qt = p.getQuantita();

            if(prod.getQuantita() < qt)
                throw new IllegalStateException("quantità non disponibile!");

            prod.setQuantita(prod.getQuantita()-p.getQuantita());

            op.setOrdine(newOrdine);
            op.setProdotto(prod);
            op.setQuantita(p.getQuantita());

            prodottoOrdineRepository.save(op);
        }//for

        u.getCarrello().clear();
        entityManager.flush();

        //SHRINKING
        for(ProdottoCarrello p : prodottoCarrelloRepository.findByUtente(u)) entityManager.lock(p.getProdotto(), LockModeType.NONE);

        return newOrdine;
    }//registraOrdine


    @Transactional(readOnly = false)
    public List<ProdottoCarrello> getProdottiCarrello(String email) {
        return prodottoCarrelloRepository.findByUtente_Email(email);
    }//getProdottiCarrello

}//CarrelloService
