package com.ministore.ministoreserver.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "prodottiCarrelli", schema = "public")
public class ProdottoCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double subTotale;
    private Integer quantita;

    public ProdottoCarrello() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }


    @Basic
    @Column(name = "quantita")
    public Integer getQuantita() { return quantita; }
    public void setQuantita(Integer quantita) { this.quantita = quantita; }

    @Basic
    @Column(name = "subTotale")
    public Double getSubTotale() { return subTotale; }
    public void setSubTotale(Double subTotale) { this.subTotale = subTotale; }

    @ManyToOne
    private Utente utente;

    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }

    @ManyToOne
    private Prodotto prodotto;

    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProdottoCarrello)) return false;

        ProdottoCarrello pc = (ProdottoCarrello) o;

        return pc.getId() == this.getId() && pc.getUtente().equals(this.getUtente());
    }//equals

    @Override
    public int hashCode() {
        return Objects.hash(id, utente, prodotto, quantita);
    }//hashCode

}//ProdottoCarrello
