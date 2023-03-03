package com.ministore.ministoreserver.controllers;

import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.Prodotto;
import com.ministore.ministoreserver.entities.Utente;
import com.ministore.ministoreserver.services.OrdineService;
import com.ministore.ministoreserver.services.ProdottoOrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/ordini")
public class OrdineController {

    @Autowired
    private OrdineService service;
    private ProdottoOrdineService poService;

    @GetMapping(path = "/viewAll")
    public List<Ordine> getAll() {
        return service.getAll();
    }//getAll

    @GetMapping(path = "/{id}")
    public Ordine getById(@PathVariable("id") int id) {
        return service.getById(id);
    }//getById

    @GetMapping(path = "/date")
    public List<Ordine> getByData(@RequestBody Date data) {
        return service.getByData(data);
    }//getByData

    @GetMapping(path = "/user")
    public List<Ordine> getByUtente(@RequestBody Utente utente) {
        return service.getByUtente(utente);
    }//getByUtente

    @GetMapping(path = "/address")
    public List<Ordine> getByIndirizzo(@RequestBody String indirizzo) { return service.getByIndirizzo(indirizzo); }

    @PostMapping(path = "/add")
    public void addOrdine(@RequestBody Ordine ordine) {
        service.addOrdine(ordine);
    }//addOrdine

    @PostMapping(path = "/update")
    public void updateOrdine(@RequestBody Ordine ordine) {
        service.updateOrdine(ordine);
    }//updateOrdine

    @DeleteMapping(path = "/remove/{id}")
    public void removeOrdine(@PathVariable("id") int id) {
        service.removeOrdine(id);
    }//removeOrdine

    @GetMapping(path = "/prodotti")
    public List<Prodotto> getProdotti(int id) {
        return poService.getProdottiByOrdine(id);
    }//getProdotti

}//OrdineController
