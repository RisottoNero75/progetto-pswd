package com.ministore.ministoreserver.repositories;

import com.ministore.ministoreserver.entities.ProdottoCarrello;
import com.ministore.ministoreserver.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoCarrelloRepository extends JpaRepository<ProdottoCarrello, Integer> {

    List<ProdottoCarrello> findByUtente(Utente u);
    List<ProdottoCarrello>  findByUtente_Id(int id);
    List<ProdottoCarrello> findByUtente_Email(String email);
    ProdottoCarrello findById(int id);

}//ProdottoCarrelloRepository
