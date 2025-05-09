package com.hortifruti.hortifrutiapi.controller;

import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorRequestDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorResponseDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeRequestDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import com.hortifruti.hortifrutiapi.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("/buscar-todos-fornecedores")
    public List<Fornecedor> buscarTodos(){
        List<Fornecedor> fornecedores = fornecedorService.buscarTodos();
        return fornecedores;
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> inserirNovoFornecedor(@RequestBody @Valid FornecedorRequestDTO fornecedor) {
        FornecedorResponseDTO novoFornecdor = fornecedorService.criarFornecedor(fornecedor);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoFornecdor.id())
                .toUri();
        return ResponseEntity.created(uri).body(novoFornecdor);
    }
}
