package com.hortfruit.hortfruitapi.service;

import com.hortfruit.hortfruitapi.model.Produto;
import com.hortfruit.hortfruitapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> buscaTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos;
    }

}
