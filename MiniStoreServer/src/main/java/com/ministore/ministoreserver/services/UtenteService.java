package com.ministore.ministoreserver.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.ProdottoCarrello;
import com.ministore.ministoreserver.entities.Utente;
import com.ministore.ministoreserver.repositories.UtenteRepository;
import com.ministore.ministoreserver.support.Carrello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository repository;

    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Utente> getAll() {
        return repository.findAll();
    }//getAll

    @Transactional(readOnly = true)
    public Utente getUtente(int id) {
        return repository.findById(id);
    }//getUtente

    @Transactional(readOnly = true)
    public Utente getUtenteByUsername(String username) {
        return repository.findByUsername(username);
    }//getUtenteByUsername

    @Transactional(readOnly = true)
    public Utente getUtenteByEmail(String email) {
        return repository.findByEmail(email);
    }//getUtenteByEmail

    @Transactional(readOnly = true)
    public List<Utente> getUtenteByNome(String nome) {
        return repository.findByNome(nome);
    }//getUtenteByNome

    @Transactional(readOnly = true)
    public List<Utente> getUtenteByCognome(String cognome) {
        return repository.findByCognome(cognome);
    }//getUtenteByCognome

    @Transactional(readOnly = true)
    public List<Utente> getUtenteByNomeAndCognome(String nome, String cognome) {
        return repository.findByNomeAndCognome(nome, cognome);
    }//getUtenteByNomeAndCognome

    @Transactional(readOnly = false)
    public Carrello getCarrello( ){
        Utente u= getUtente(0); //da fare
        List<ProdottoCarrello> prodotti = (List<ProdottoCarrello>) u.getCarrello();
        return new Carrello(prodotti);
    }

    @Transactional
    public void addUtente(Utente utente) {
        if(repository.existsById(utente.getId()))
            throw new IllegalArgumentException("Utente già presente");

        repository.save(utente);
    }//addUtente

    @Transactional
    public void updateUtente(Utente toUpdate) {
        if(!repository.existsById(toUpdate.getId()))
            throw new IllegalArgumentException("Utente non trovato.");

        Utente old = getUtente(toUpdate.getId());
        em.lock(old, LockModeType.OPTIMISTIC);

        if(!toUpdate.getNome().equals("")) old.setNome(toUpdate.getNome());
        if(!toUpdate.getCognome().equals("")) old.setCognome(toUpdate.getCognome());
        if(!toUpdate.getUsername().equals("")) old.setUsername(toUpdate.getUsername());
        if(!toUpdate.getEmail().equals("")) old.setEmail(toUpdate.getEmail());
        if(!toUpdate.getPassword().equals("")) old.setPassword(toUpdate.getPassword());

        em.flush();
        em.lock(old, LockModeType.NONE);
    }//updateUtente

    @Transactional
    public void deleteUtente(int id) {
        if(!repository.existsById(id))
            throw new IllegalArgumentException("Utente non trovato.");

        repository.deleteById(id);
        em.flush();
    }//deleteUtente

    @Transactional(readOnly = true)
    public Collection<Ordine> getOrdini(int id) {
        return getUtente(id).getOrdini();
    }//getOrdini

    @Transactional
    public void addOrdine(int id, Ordine ordine) {
        if(!repository.existsById(id))
            throw new IllegalArgumentException("Utente non trovato.");

        getUtente(id).getOrdini().add(ordine);

        em.flush();
    }//setOrdini

    @Transactional(readOnly = false)
    public void removeOrdine(int id, Ordine ordine) {
        if(!repository.existsById(id))
            throw new IllegalArgumentException("Utente non trovato.");

        Utente utente = repository.findById(id);
        em.lock(utente, LockModeType.OPTIMISTIC);

        utente.getOrdini().remove(ordine);

        em.flush();
        em.lock(utente, LockModeType.NONE);
    }//removeOrdine

    @Transactional(readOnly = false)
    public Utente getUtente(){
        String email= getUserEmail();
        if(repository.existsByEmail(email))
            return getUtente(email);
        return accounting();
    }//getUtente

    @Transactional(readOnly = false)
    public Utente getUtente(String email){
        return repository.findByEmail(email);
    }//getUtente

    @Transactional(readOnly = false)
    public  String getUserEmail(){
        String email = getTokenNode().get("claims").get("email").asText();
        System.out.println("email is"+ email);
        return  email;
    }

    private JsonNode getTokenNode() {
        OAuth2ResourceServerProperties.Jwt jwt = (OAuth2ResourceServerProperties.Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        String jwtAsString;
        JsonNode jsonNode;
        try {
            jwtAsString = objectMapper.writeValueAsString(jwt);
            jsonNode = objectMapper.readTree(jwtAsString);
        } catch (JsonProcessingException e) {
            e.getMessage();
            throw new RuntimeException("Unable to retrieve user's info!");
        }
        return jsonNode;
    }//getTokenNode

    @Transactional
    public Utente accounting(){
        String email = getUserEmail();
        Utente u=new Utente();

        u.setEmail(email);
        u.setNome(getTokenNode().get("claims").get("name").asText());
        u.setCarrello(new LinkedList<>());
        u.setOrdini(new LinkedList<>());

        repository.saveAndFlush(u);

        return u;
    }//accounting

}//UtenteService
