package com.ministore.ministoreserver.controllers;

import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.Prodotto;
import com.ministore.ministoreserver.entities.ProdottoCarrello;
import com.ministore.ministoreserver.services.CarrelloService;
import com.ministore.ministoreserver.services.CategoriaService;
import com.ministore.ministoreserver.services.ProdottoService;
import com.ministore.ministoreserver.services.UtenteService;
import com.ministore.ministoreserver.support.Carrello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeSet;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CarrelloService carrelloService;

    @GetMapping
    public List<Prodotto> getProdotti() {
        return prodottoService.getAll();
    }//getProdotti

    @PostMapping("/orderreg")
    @ResponseBody
    public ResponseEntity registraOridne(@RequestParam("indirizzo") String indirizzo) {
        Ordine o = carrelloService.registraOrdine(indirizzo);
        return new ResponseEntity(o, HttpStatus.OK);
    }//registraOridne

    @GetMapping("/categorie")
    private TreeSet<String> getCategorie() {
        return categoriaService.getShowAllCategories();
    }//getCategorie

    @GetMapping("/cart")
    private Carrello getCarrello() {
        return utenteService.getCarrello();
    }//getCarrello

    @PostMapping("/updatecart")
    private ResponseEntity setCarrello( @RequestBody Carrello carrello) {
        List<ProdottoCarrello> newCarrello = carrelloService.updateCarrello( carrello.getProdotti());

        if (newCarrello != null)
            return new ResponseEntity(newCarrello, HttpStatus.OK);

        return new ResponseEntity("Error", HttpStatus.BAD_REQUEST);
    }//setCarrello

    @PostMapping("/addtocart")
    @ResponseBody
    private ResponseEntity addToCart( @RequestBody ProdottoCarrello prodotto) {
        ProdottoCarrello p = carrelloService.addProdotto(prodotto);
        return new ResponseEntity(p, HttpStatus.OK);
    }//addToCart

}//ShopController