package com.ministore.ministoreserver.repositories;

import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {

    List<Ordine> findAll();

    Ordine findById(int id);
    List<Ordine> findByData(Date data);
    List<Ordine> findByUtente(Utente utente);
    List<Ordine> findByIndirizzo(String indirizzo);

    boolean existsById(int id);

}//OrdineRepository
