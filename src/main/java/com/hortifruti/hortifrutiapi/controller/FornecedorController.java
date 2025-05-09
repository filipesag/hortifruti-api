package com.hortifruti.hortifrutiapi.controller;

import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorContratoCanceladoDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorRequestDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorResponseDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorUpdateDTO;
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
import java.util.UUID;

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
    public ResponseEntity<FornecedorResponseDTO> inserirNovoFornecedor(@RequestBody @Valid FornecedorRequestDTO dto) {
        FornecedorResponseDTO novoFornecedor = fornecedorService.criarFornecedor(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoFornecedor.id())
                .toUri();
        return ResponseEntity.created(uri).body(novoFornecedor);
    }

    @PutMapping("/cancela-contrato/{id}")
    public ResponseEntity<FornecedorContratoCanceladoDTO> cancelaContrato(@PathVariable UUID id, @RequestBody FornecedorUpdateDTO dto) {
        FornecedorResponseDTO fornecedorCancelado = fornecedorService.rescindeContrato(id, dto);
        String msgSucesso = "Contrato cancelado com sucesso!";
        return ResponseEntity.ok().body(new FornecedorContratoCanceladoDTO(msgSucesso, fornecedorCancelado));
    }

    @PutMapping("/atualiza-fornecedor/{id}")
    public ResponseEntity<FornecedorResponseDTO> atualizaFornecedor(@PathVariable UUID id, @RequestBody FornecedorUpdateDTO fornecedor) {
        FornecedorResponseDTO novoFornecedor = fornecedorService.atualizaFornecedor(id, fornecedor);
        return ResponseEntity.ok().body(novoFornecedor);
    }

}
