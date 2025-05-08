package com.hortfruit.hortfruitapi.service;

import com.hortfruit.hortfruitapi.model.Fornecedor;
import com.hortfruit.hortfruitapi.model.Produto;
import com.hortfruit.hortfruitapi.repository.FornecedorRepository;
import com.hortfruit.hortfruitapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Fornecedor> buscarTodos() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return fornecedores;
    }

}
