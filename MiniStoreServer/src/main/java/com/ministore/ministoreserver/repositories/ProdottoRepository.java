package com.ministore.ministoreserver.repositories;

import com.ministore.ministoreserver.entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {

    Prodotto findByid(int id);

    //List<Prodotto> findByprezzo(float prezzo);

    boolean existsById(int id);
    boolean deleteById(int id);

}//ProdottoRepository
