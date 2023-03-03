package com.ministore.ministoreserver.repositories;

import com.ministore.ministoreserver.entities.Ordine;
import com.ministore.ministoreserver.entities.Prodotto;
import com.ministore.ministoreserver.entities.ProdottoOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoOrdineRepository extends JpaRepository<ProdottoOrdine, Integer> {

    ProdottoOrdine findById(int idProdotto, int idOrdine);
    List<ProdottoOrdine> findByOrdine(Ordine ordine);
    List<ProdottoOrdine> findByProdotto(Prodotto prodotto);

    List<Integer> findProdottoByOrdine(int idOrdine);
    List<Integer> findOrdineByProdotto(int idProdotto);

    List<ProdottoOrdine> findAll();

    boolean existByProdotto_id(int idProdotto);
    boolean existsByOrdine_id(int idOrdine);

}//ProdottoOrdineRepository
