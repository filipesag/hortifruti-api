package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.model.Produto;
import com.hortifruti.hortifrutiapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> buscaTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos;
    }

}
