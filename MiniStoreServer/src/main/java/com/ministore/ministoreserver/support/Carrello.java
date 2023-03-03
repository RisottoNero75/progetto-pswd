package com.ministore.ministoreserver.support;

import com.ministore.ministoreserver.entities.ProdottoCarrello;

import java.util.List;
import java.util.Objects;

public class Carrello {

    private List<ProdottoCarrello> prodotti;
    private double totale;
    private int numArticoli;

    public Carrello(List<ProdottoCarrello> prodotti) {
        this.prodotti = prodotti;
        this.totale=0;
        this.numArticoli=0;

        for(ProdottoCarrello p : prodotti){
            totale += p.getQuantita()*p.getProdotto().getPrezzo();
            numArticoli += p.getQuantita();
        }//for
    }//costruttore

    public List<ProdottoCarrello> getProdotti() { return prodotti; }
    public void setProdotti(List<ProdottoCarrello> prodotti) { this.prodotti = prodotti; }

    public double getTotale() { return totale; }
    public void setTotale(double totale) { this.totale = totale; }

    public int getNumArticoli() { return numArticoli; }
    public void setNumArticoli(int numArticoli) { this.numArticoli = numArticoli; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrello carrello = (Carrello) o;
        return Objects.equals(prodotti, carrello.prodotti);
    }//equals

    @Override
    public int hashCode() {
        return Objects.hash(prodotti);
    }//hashCode

}//Carrello
