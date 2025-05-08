package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.model.Fornecedor;
import com.hortifruti.hortifrutiapi.repository.FornecedorRepository;
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
