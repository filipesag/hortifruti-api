package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorRequestDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorResponseDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorUpdateDTO;
import com.hortifruti.hortifrutiapi.mappers.fornecedor.FornecedorMapper;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import com.hortifruti.hortifrutiapi.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    public List<Fornecedor> buscarTodos() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return fornecedores;
    }

    @Transactional
    public FornecedorResponseDTO criarFornecedor(FornecedorRequestDTO dto){
        Fornecedor novoFornecedor = fornecedorMapper.toEntity(dto);
        novoFornecedor.setIsAtivo(true);
        fornecedorRepository.save(novoFornecedor);
        return fornecedorMapper.toDTO(novoFornecedor);
    }

    public FornecedorResponseDTO rescindeContrato(UUID id, FornecedorUpdateDTO dto) {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        Fornecedor fornecedorCancelado = fornecedor.get();
        fornecedorCancelado.setIsAtivo(false);
        return fornecedorMapper.toDTO(fornecedorRepository.save(fornecedorCancelado));
    }

    public FornecedorResponseDTO atualizaFornecedor(UUID id, FornecedorUpdateDTO dto) {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        Fornecedor fornecedorAtualizado = fornecedor.get();
        fornecedorAtualizado.setCnpj(dto.cnpj());
        fornecedorAtualizado.setCidade(dto.cidade());
        fornecedorAtualizado.setEstado(dto.estado());
        fornecedorAtualizado.setNome(dto.nome());
        fornecedorAtualizado.setEmail(dto.email());
        fornecedorAtualizado.setTelefone(dto.telefone());
        return fornecedorMapper.toDTO(fornecedorRepository.save(fornecedorAtualizado));
    }

}
