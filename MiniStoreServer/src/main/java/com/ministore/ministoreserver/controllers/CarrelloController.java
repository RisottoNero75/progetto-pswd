package com.ministore.ministoreserver.controllers;

import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.ProdottoCarrello;
import com.ministore.ministoreserver.entities.Utente;
import com.ministore.ministoreserver.services.CarrelloService;
import com.ministore.ministoreserver.services.UtenteService;
import com.ministore.ministoreserver.support.Carrello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/cart")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;
    @Autowired
    private UtenteService utenteService;

    @PostMapping(value = "/orderreg", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registraOridne(@RequestBody String indirizzo) {
        System.out.println(indirizzo);
        Ordine o = carrelloService.registraOrdine(indirizzo);
        return new ResponseEntity(o, HttpStatus.OK);
    }//registraOridne

    @GetMapping
    @ResponseBody
    public Carrello getProdottiInCarr() {
        Utente u = utenteService.getUtente(); //da fare
        Carrello carr = new Carrello(carrelloService.getProdottiCarrello(u.getEmail()));
        return carr;
    }//getProdottiInCarr

    @GetMapping("/empty")
    @ResponseBody
    public ResponseEntity emptyCarrello() {
        carrelloService.emptyCart();
        return new ResponseEntity(HttpStatus.OK);
    }//emptyCarrello

    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity removeProdotto(@RequestBody ProdottoCarrello prodottoInCarrello) {
        carrelloService.removeProdottoCarrello(prodottoInCarrello);
        return new ResponseEntity(prodottoInCarrello, HttpStatus.OK);
    }//removeProdotto

}//CarelloController