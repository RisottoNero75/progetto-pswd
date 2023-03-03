package com.ministore.ministoreserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "ordini", schema = "public")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date data;
    private String indirizzo;

    public Ordine() {}

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    @Basic
    @Column(name = "data")
    public Date getData() { return this.data; }
    public void setData(Date data) { this.data = data; }

    @Basic
    @Column(name = "indirizzo")
    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    @ManyToOne
    private Utente utente;

    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }

    @OneToMany(mappedBy = "ordine")
    private Collection<ProdottoOrdine> prodottiOrdini;

    public Collection<ProdottoOrdine> getProdottiOrdini() { return prodottiOrdini; }
    public void setProdottiOrdini(Collection<ProdottoOrdine> prodottiOrdini) { this.prodottiOrdini = prodottiOrdini; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if(!(o instanceof Ordine)) return false;

        Ordine or = (Ordine)o;

        return or.getId() == id;
    }//equals

    @Override
    public String toString() {
        return "Id: "+ id +"\n"+
                "Data: "+ data.toString() +"\n" +
                "Indirizzo: "+ indirizzo;
    }//toString

}//Ordine
