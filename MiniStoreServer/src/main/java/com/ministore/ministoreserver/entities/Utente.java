package com.ministore.ministoreserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "utenti", schema = "public")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String password;

    public Utente() {}

    //getter

    public int getId() { return this.id; }

    @Basic
    @Column(name = "nome")
    public String getNome() { return this.nome; }

    @Basic
    @Column(name = "cognome")
    public String getCognome() { return this.cognome; }

    @Basic
    @Column(name = "username")
    public String getUsername() { return this.username; }

    @Basic
    @Column(name = "email")
    public String getEmail() { return  this.email; }

    @Basic
    @Column(name = "password")
    public String getPassword() { return this.password; }

    //setter
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Ordine> ordini;

    public Collection<Ordine> getOrdini() { return this.ordini; }
    public void setOrdini(Collection<Ordine> ordini) { this.ordini = ordini; }

    @JsonIgnore
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<ProdottoCarrello> carrello;

    public Collection<ProdottoCarrello> getCarrello() { return carrello; }
    public void setCarrello(Collection<ProdottoCarrello> carrello) { this.carrello = carrello; }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;

        if(!(o instanceof Utente)) return false;

        Utente u = (Utente) o;

        return u.getId() == id;
    }//equals

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("-Username: "+ username +";\n"+
                "-Nome: "+ nome +";\n"+
                "-Cognome: "+ cognome +";\n"+
                "-Email: "+ email + ".\n\n ORDINI:");

        for(Ordine o : ordini)
            sb.append(o.toString()+"\n");


        return sb.toString();
    }//toString

}//Utente
