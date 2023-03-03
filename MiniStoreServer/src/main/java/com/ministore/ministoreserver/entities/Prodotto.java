package com.ministore.ministoreserver.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="prodotti", schema = "public")
public class Prodotto {

    private int id;
    private String nome;
    private String marca;
    private String descrizione;
    private float prezzo;
    private int quantita;
    private String categoria;

    public Prodotto() {
        nome = "none";
        marca = "none";
        descrizione = "none";
        prezzo = 0;
        quantita = 0;
    }//costruttore

    //getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public int getId() { return id; }

    @Basic
    @Column(name="nome", nullable = false)
    public String getNome() { return nome; }

    @Basic
    @Column(name= "marca", nullable = false)
    public String getMarca() { return marca; }

    @Basic
    @Column(name="descrizione")
    public String getDescrizione() { return descrizione; }

    @Basic
    @Column(name= "prezzo")
    public float getPrezzo() { return prezzo; }

    @Basic
    @Column(name= "quantita")
    public int getQuantita() { return quantita; }

    @Basic
    @Column(name = "categoria")
    public String getCategoria() { return categoria; }

    //setter
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public void setPrezzo(float prezzo) { this.prezzo = prezzo; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @OneToMany(mappedBy = "prodotto")
    private Collection<ProdottoOrdine> prodottiOrdini;

    public Collection<ProdottoOrdine> getProdottiOrdini() { return prodottiOrdini; }
    public void setProdottiOrdini(Collection<ProdottoOrdine> prodottiOrdini) { this.prodottiOrdini = prodottiOrdini; }

    @OneToMany( mappedBy = "prodotto")
    private Collection<ProdottoCarrello> carrello;

    public Collection<ProdottoCarrello> getCarrello() { return carrello; }
    public void setProdottoCarrello(Collection<ProdottoCarrello> carrello) { this.carrello = carrello; }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Prodotto)) return false;

        Prodotto p = (Prodotto)o;

        return p.getId() == id;
    }//equals

    @Override
    public String toString() {
        return "-id: "+ id +"\n"+
                "-nome: "+ nome +"\n"+
                "-marca: "+ marca +"\n"+
                "-descrizione: "+ descrizione +"\n"+
                "-prezzo: "+ prezzo +"\n"+
                "-quantià: "+ quantita +"\n";
    }//toString

}//Prodotto
