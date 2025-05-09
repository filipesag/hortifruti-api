package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorRequestDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorResponseDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeRequestDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.fornecedor.FornecedorMapper;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import com.hortifruti.hortifrutiapi.model.Sede;
import com.hortifruti.hortifrutiapi.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    public List<Fornecedor> buscarTodos() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return fornecedores;
    }

    @Transactional
    public FornecedorResponseDTO criarFornecedor(FornecedorRequestDTO fornecedor){
        Fornecedor novoFornecedor = fornecedorMapper.toEntity(fornecedor);
        fornecedorRepository.save(novoFornecedor);
        return fornecedorMapper.toDTO(novoFornecedor);
    }

}
