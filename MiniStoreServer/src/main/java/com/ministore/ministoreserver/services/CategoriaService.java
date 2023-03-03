package com.ministore.ministoreserver.services;

import com.ministore.ministoreserver.entities.Prodotto;
import com.ministore.ministoreserver.repositories.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TreeSet;

@Service
public class CategoriaService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Transactional(readOnly = true)
    public TreeSet<String> getShowAllCategories() {
        List<Prodotto> e = prodottoRepository.findAll();
        TreeSet<String> ret= new TreeSet<String>();

        for(Prodotto p: e)
            ret.add(p.getCategoria());

        return ret;
    }//getShowAllCategories

}//CategoriaService
