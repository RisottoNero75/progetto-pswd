package com.ministore.ministoreserver.controllers;

import com.ministore.ministoreserver.entities.Prodotto;
import com.ministore.ministoreserver.services.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/prodotti")
public class ProdottoController {

    @Autowired
    private ProdottoService service;

    @GetMapping(path= "/viewAll")
    public List<Prodotto> getAll() {
        return service.getAll();
    }//getAll

    @GetMapping("/forid/{id}")
    public Prodotto getProdotto(@PathVariable("id") int id) {
        return service.getProdotto(id);
    }//getProdotto

    @GetMapping(path="/fornome/{nome}")
    public List<Prodotto> getByNome(@PathVariable("nome") String nome) {
        return service.getByNome(nome);
    }//getByNome

    @GetMapping(path="/formarca/{marca}")
    public List<Prodotto> getByMarca(@PathVariable("marca") String marca) {
        return service.getByMarca(marca);
    }//getByMarca

    @GetMapping(path="/forprezzo/{prezzo}")
    public List<Prodotto> getByPrezzo(@PathVariable("prezzo") float prezzo) {
        return service.getByPrezzo(prezzo);
    }//getByPrezzo

    @PostMapping(path="/up-prod")
    public void updateProdotto(Prodotto p) {
        service.updateProdotto(p);
    }//updateProdotto

    @PutMapping(path= "/add-prod")
    public void addProdotto(@RequestBody Prodotto p) {
        service.addProdotto(p);
    }//addProdotto

    @DeleteMapping(path ="/rem-prod/{id}")
    public boolean deleteProdotto(@PathVariable("id") int id) {
        return service.deleteProdotto(id);
    }//deleteProdotto

    @PostMapping(path= "/up-prod/add-quant/{id}")
    public void addQuantita(@PathVariable("id") int id) {
        service.addQuantita(id);
    }//addQuantita

    @PostMapping(path= "/up-prod/rem-quant/{id}")
    public void removeQuantita(@PathVariable("id") int id) {
        service.removeQuantita(id);
    }//removeQuantita

    @GetMapping(path="/disp/{id}")
    public boolean disponibilita(@PathVariable("id") int id) {
        return service.isDisponibile(id);
    }//disponibilita

    @PostMapping(path="/up-prod/prezzo/{id}")
    public void setNewPrezzo(@PathVariable("id") int id, @RequestBody float newPrezzo){
        service.setNewPrezzo(id, newPrezzo);
    }//setNewPrezzo

    @GetMapping(path="/prezzo-max/{prezzo}")
    public List<Prodotto> getByPrezzoGreaterThan(@PathVariable("prezzo") float prezzo) {
        if(prezzo < 0)
            throw new IllegalArgumentException("Valore non corretto");

        return service.getByPrezzoGreaterThan(prezzo);
    }//getByPrezzoGreaterThan

    @GetMapping(path="/prezzo-min/{prezzo}")
    public List<Prodotto> getByPrezzoLesserThan(@PathVariable("prezzo") float prezzo) {
        if(prezzo < 0)
            throw new IllegalArgumentException("Valore non corretto");

        return service.getByPrezzoLesserThan(prezzo);
    }//getByPrezzoLesserThan

    /*
    @GetMapping(path="/list/{prezzoMin, prezzoMax}")
    public List<Prodotto> getByRangePrezzo(@PathVariable("prezzoMin") float prezzoMin, @PathVariable("prezzoMax") float prezzoMax) {
        if(prezzoMax<0 || prezzoMin<0)
            throw new IllegalArgumentException("Valori non corretti");

        return service.getByRangePrezzo(prezzoMin, prezzoMax);
    }//getByRangePrezzo
    */

}//ProdottoController
