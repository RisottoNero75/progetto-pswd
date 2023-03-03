package com.ministore.ministoreserver.services;

import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.Prodotto;
//import com.ministore.ministoreserver.entities.ProdottoOrdine;
import com.ministore.ministoreserver.repositories.ProdottoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository repository;

    private EntityManager em;

    @Transactional(readOnly = true)
    public Prodotto getProdotto(int id) {
        return repository.findByid(id);
    }//getById

    @Transactional(readOnly = true)
    public List<Prodotto> getAll() {
        return repository.findAll();
    }//getAll

    @Transactional(readOnly = true)
    public List<Prodotto> getByNome(String nome) {
        ArrayList<Prodotto> ris = new ArrayList<>();

        ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>)repository.findAll();
        for(Prodotto p : prodotti)
            if(p.getNome().equals(nome))
                ris.add(p);

        return ris;
    }//getByNome

    @Transactional(readOnly = true)
    public List<Prodotto> getByMarca(String marca) {
        ArrayList<Prodotto> ris = new ArrayList<>();

        ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>)repository.findAll();
        for(Prodotto p : prodotti)
            if(p.getMarca().equals(marca))
                ris.add(p);

        return ris;
    }//getByMarca

    @Transactional(readOnly = true)
    public List<Prodotto> getByPrezzo(float prezzo) {
        ArrayList<Prodotto> ris = new ArrayList<>();

        ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>)repository.findAll();
        for(Prodotto p : prodotti)
            if(p.getPrezzo() == prezzo)
                ris.add(p);

        return ris;
    }//getByPrezzo

    @Transactional(readOnly = true)
    public List<Prodotto> getByPrezzoGreaterThan(float prezzo) {
        ArrayList<Prodotto> ris = new ArrayList<>();

        ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>)repository.findAll();
        for(Prodotto p : prodotti)
            if(p.getPrezzo() >= prezzo)
                ris.add(p);

        return ris;
    }//findByPrezzoGreaterThan

    @Transactional(readOnly = true)
    public List<Prodotto> getByPrezzoLesserThan(float prezzo) {
        ArrayList<Prodotto> ris = new ArrayList<>();

        ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>)repository.findAll();
        for(Prodotto p : prodotti)
            if(p.getPrezzo() <= prezzo)
                ris.add(p);

        return ris;
    }//findByPrezzoLesserThan


    @Transactional(readOnly = true)
    public List<Prodotto> getByRangePrezzo(float minPrezzo, float maxPrezzo) {
        ArrayList<Prodotto> ris = new ArrayList<>();

        ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>)repository.findAll();
        for(Prodotto p : prodotti)
            if(p.getPrezzo() >= minPrezzo && p.getPrezzo() <= maxPrezzo)
                ris.add(p);

        return ris;
    }//findByRangePrezzo


    @Transactional
    public void addProdotto(Prodotto toAdd) {
        ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>) repository.findAll();

        for(Prodotto p: prodotti) {
            if(p.equals(toAdd))
                throw new IllegalArgumentException("Prodotto già esistente.");
        }//for

        repository.save(toAdd);
    }//addProdotto

    @Transactional
    public boolean deleteProdotto(int id) {
        if(!repository.existsById(id)) return false;

        repository.deleteById(id);
        repository.flush();

        return true;
    }//deleteById

    @Transactional
    public void updateProdotto(Prodotto toUpdate) {
        if(!repository.existsById(toUpdate.getId()))
            throw new IllegalArgumentException("Prodotto inesistente.");

        Prodotto old = em.find(Prodotto.class, toUpdate.getId());
        em.lock(old, LockModeType.OPTIMISTIC);

        String newNome = toUpdate.getNome();
        String newMarca = toUpdate.getMarca();
        String newDescrizione = toUpdate.getDescrizione();
        float newPrezzo = toUpdate.getPrezzo();
        int newQuantita = toUpdate.getQuantita();

        if(newNome != null) old.setNome(newNome);
        if(newMarca != null) old.setMarca(newMarca);
        if(newDescrizione != null) old.setDescrizione(newDescrizione);
        if(newPrezzo >= 0) old.setPrezzo(newPrezzo);
        if(newQuantita >= 0) old.setQuantita(newQuantita);

        em.flush();
        em.lock(old, LockModeType.NONE);
    }//updateProdotto

    @Transactional
    public void addQuantita(int id) {
        if(!repository.existsById(id))
            throw new IllegalArgumentException("Prodotto inesistente.");

        Prodotto old = em.find(Prodotto.class, id);
        em.lock(old, LockModeType.OPTIMISTIC);

        old.setQuantita(old.getQuantita()+1);

        em.flush();
        em.lock(old, LockModeType.NONE);
    }//addQauntita

    @Transactional
    public void removeQuantita(int id) {
        if(!repository.existsById(id))
            throw new IllegalArgumentException("Prodotto inesistente.");

        Prodotto old = em.find(Prodotto.class, id);
        em.lock(old, LockModeType.OPTIMISTIC);

        old.setQuantita(old.getQuantita()-1);

        em.flush();
        em.lock(old, LockModeType.NONE);
    }//removeQuantita

    @Transactional(readOnly = true)
    public boolean isDisponibile(int id) {
        if(!repository.existsById(id))
            throw new IllegalArgumentException("Prodotto inesistente.");

        Prodotto p = repository.findByid(id);

        return p.getQuantita() > 0;
    }//isDisponibile

    @Transactional
    public void setNewPrezzo(int id, float newPrezzo) {
        if(!repository.existsById(id))
            throw new IllegalArgumentException("Prodotto inesistente.");

        Prodotto old = em.find(Prodotto.class, id);
        em.lock(old, LockModeType.OPTIMISTIC);

        old.setPrezzo(newPrezzo);

        em.flush();
        em.lock(old, LockModeType.NONE);
    }//setNewPrezzo

}//ProdottoService
