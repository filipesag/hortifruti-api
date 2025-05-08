package com.hortfruit.hortfruitapi.controller;

import com.hortfruit.hortfruitapi.model.Fornecedor;
import com.hortfruit.hortfruitapi.model.Produto;
import com.hortfruit.hortfruitapi.service.FornecedorService;
import com.hortfruit.hortfruitapi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public List<Fornecedor> buscarTodos(){
        List<Fornecedor> fornecedores = fornecedorService.buscarTodos();
        return fornecedores;
    }
}
