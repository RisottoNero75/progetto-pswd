package com.ministore.ministoreserver.services;

import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.Prodotto;
import com.ministore.ministoreserver.entities.ProdottoOrdine;
import com.ministore.ministoreserver.repositories.OrdineRepository;
import com.ministore.ministoreserver.repositories.ProdottoOrdineRepository;
import com.ministore.ministoreserver.repositories.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProdottoOrdineService {

    @Autowired
    ProdottoOrdineRepository repository;

    @Autowired
    ProdottoRepository prodottoRepository;
    OrdineRepository ordineRepository;

    EntityManager em;

    @Transactional(readOnly = true)
    public List<ProdottoOrdine> getAll() { return repository.findAll(); }

    @Transactional(readOnly = true)
    public List<Prodotto> getProdottiByOrdine(int idOrdine) {
        ArrayList<Integer> idOrdini = (ArrayList<Integer>)repository.findProdottoByOrdine(idOrdine);
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        for(int id : idOrdini)
            prodotti.add(prodottoRepository.findByid(id));

        return prodotti;
    }//getProdottiByOrdine

    @Transactional(readOnly = true)
    public List<Ordine> getOrdiniByProdotto(int idProdotto) {
        ArrayList<Integer> idProdotti = (ArrayList<Integer>)repository.findProdottoByOrdine(idProdotto);
        ArrayList<Ordine> ordini = new ArrayList<>();

        for(int id : idProdotti)
            ordini.add(ordineRepository.findById(id));

        return ordini;
    }//getOrdiniByProdotto

    @Transactional
    public void addProdottoOrdine(ProdottoOrdine po) {
        if(repository.existByProdotto_id(po.getProdotto().getId()) &&
                repository.existsByOrdine_id(po.getOrdine().getId()))
            throw new IllegalArgumentException("Già presente");

        repository.save(po);
    }//addProdottoOrdine

    @Transactional
    public void removeProdottoOrdine(int idProdotto, int idOrdine) {
        if(!repository.existByProdotto_id(idProdotto) && !repository.existsByOrdine_id(idOrdine))
            throw new IllegalArgumentException("Non presente.");

        ProdottoOrdine old = repository.findById(idProdotto, idOrdine);
        repository.delete(old);
    }//removeProdottoOrdine

    @Transactional
    public void removeProdottoOrdine(ProdottoOrdine po) {
        if(!repository.existByProdotto_id(po.getProdotto().getId()) && !repository.existsByOrdine_id(po.getOrdine().getId()))
            throw new IllegalArgumentException("Non presente.");

        ProdottoOrdine old = repository.findById(po.getProdotto().getId(), po.getOrdine().getId());
        repository.delete(old);
    }//removeProdottoOrdine

}//ProdottoOrdineService
