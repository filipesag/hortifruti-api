package com.hortfruit.hortfruitapi.controller;

import com.hortfruit.hortfruitapi.model.Produto;
import com.hortfruit.hortfruitapi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> buscarTodos(){
        List<Produto> produtos = produtoService.buscaTodos();
        return produtos;
    }
}
