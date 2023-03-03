package com.ministore.ministoreserver.controllers;

import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.Utente;
import com.ministore.ministoreserver.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService service;

    @GetMapping(path = "/viewAll")
    public List<Utente> getAll() {
        return service.getAll();
    }//getAll

    @GetMapping(path = "/{id}")
    public Utente getById(@PathVariable("id") int id) {
        return service.getUtente(id);
    }//getById

    @GetMapping(path = "/username")
    public Utente getByUsername(@RequestBody String username) {
        return service.getUtenteByUsername(username);
    }//getByUsername

    @GetMapping(path = "/email")
    public Utente getByEmail(@RequestBody String email) {
        return service.getUtenteByEmail(email);
    }//getByEmail

    @GetMapping(path = "/nome/{nome}")
    public List<Utente> getById(@PathVariable("nome") String nome) {
        return service.getUtenteByNome(nome);
    }//getByNome

    @GetMapping(path = "/cognome/{cognome}")
    public List<Utente> getByCognome(@PathVariable("cognome") String cognome) {
        return service.getUtenteByCognome(cognome);
    }//getByCognome

    @GetMapping(path = "/nome-cognome")
    public List<Utente> getByNomeAndCognome(@RequestBody String nome ,String cognome) {
        return service.getUtenteByNomeAndCognome(nome, cognome);
    }//getByNomeAndCognome

    @PostMapping(path = "/add")
    public void addUtente(@RequestBody Utente toAdd) {
        service.addUtente(toAdd);
    }//addUtente

    @PostMapping(path = "/update")
    public void updateUtente(@RequestBody Utente toUpdate) {
        service.updateUtente(toUpdate);
    }//updateUtente

    @DeleteMapping(path = "/remove/{id}")
    public void removeUtente(@PathVariable("id") int id) {
        service.deleteUtente(id);
    }//removeUtente

    @GetMapping(path = "/ordini/{id}")
    public Collection<Ordine> getOrdini(@PathVariable("id") int id) {
        return service.getOrdini(id);
    }//getOrdini

    @PostMapping(path = "/ordini/{id}")
    public void addOrdine(@PathVariable("id") int id, @RequestBody Ordine ordine) {
        service.addOrdine(id, ordine);
    }//addOrdine

    @PostMapping(path = "/ordini/remove/{id}")
    public void removeOrdine(@PathVariable("id") int id, @RequestBody Ordine ordine) {
        service.removeOrdine(id, ordine);
    }//removeOrdine

}//UtenteController
