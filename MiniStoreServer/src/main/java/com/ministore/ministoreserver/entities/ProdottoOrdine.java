package com.ministore.ministoreserver.entities;

import javax.persistence.*;

@Entity
@Table(name = "prodottiOrdini", schema = "public")
public class ProdottoOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantita;

    public ProdottoOrdine() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    @ManyToOne
    private Prodotto prodotto;

    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }

    @ManyToOne
    private Ordine ordine;

    public Ordine getOrdine() { return ordine; }
    public void setOrdine(Ordine ordine) { this.ordine = ordine; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if(!(o instanceof ProdottoOrdine)) return false;

        ProdottoOrdine po = (ProdottoOrdine)o;

        return this.id == po.getId();
    }//equals

    @Override
    public String toString() {
        return "Prodotto: "+ prodotto.toString() +
                "Ordine: "+ ordine.toString();
    }//toString

}//ProdottoOrdine
