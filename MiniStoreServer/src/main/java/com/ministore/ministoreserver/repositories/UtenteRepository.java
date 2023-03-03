package com.ministore.ministoreserver.repositories;

import com.ministore.ministoreserver.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    List<Utente> findAll();

    Utente findById(int id);
    Utente findByUsername(String username);
    Utente findByEmail(String email);
    List<Utente> findByNome(String nome);
    List<Utente> findByCognome(String cognome);
    List<Utente> findByNomeAndCognome(String nome, String cognome);

    boolean existsById(int id);
    boolean existsByEmail(String email);

}//UtenteRepository
