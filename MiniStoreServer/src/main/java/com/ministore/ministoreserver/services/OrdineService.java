package com.ministore.ministoreserver.services;

import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.Utente;
import com.ministore.ministoreserver.repositories.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository repository;

    private EntityManager em;

    @Transactional(readOnly = true)
    public Ordine getById(int id) { return repository.findById(id); }//getById

    @Transactional(readOnly = true)
    public List<Ordine> getByData(Date data) {
        return repository.findByData(data);
    }//getByData

    @Transactional(readOnly = true)
    public List<Ordine> getByUtente(Utente utente) {
        return repository.findByUtente(utente);
    }//getByData

    @Transactional(readOnly = true)
    public List<Ordine> getAll() {
        return repository.findAll();
    }//getAll

    @Transactional(readOnly = true)
    public List<Ordine> getByIndirizzo(String indirizzo) { return repository.findByIndirizzo(indirizzo); }

    @Transactional
    public void addOrdine(Ordine toAdd) {
        if(repository.existsById(toAdd.getId()))
            throw new IllegalArgumentException("Ordine già presente.");

        repository.save(toAdd);
    }//addOrdine

    @Transactional
    public void updateOrdine(Ordine toUpdate) {
        if(repository.existsById(toUpdate.getId()))
            throw new IllegalArgumentException("Ordine non trovato.");

        Ordine old = repository.findById(toUpdate.getId());
        em.lock(old, LockModeType.OPTIMISTIC);

        if(toUpdate.getData() != null) old.setData(toUpdate.getData());
        if(toUpdate.getUtente() != null) old.setUtente(toUpdate.getUtente());
        if(toUpdate.getIndirizzo() != null) old.setIndirizzo(toUpdate.getIndirizzo());

        em.flush();
        em.lock(old, LockModeType.NONE);
    }//updateOrdine

    @Transactional
    public void removeOrdine(int id) {
        if(!repository.existsById(id))
            throw new IllegalArgumentException("Ordine non trovato!");

        Ordine toRemove = em.find(Ordine.class, id);
        repository.delete(toRemove);
    }//removeOrdine

}//OrdineService
