package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorRequestDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorResponseDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorUpdateDTO;
import com.hortifruti.hortifrutiapi.mappers.fornecedor.FornecedorMapper;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import com.hortifruti.hortifrutiapi.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FornecedorService {


    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    @Transactional
    public List<FornecedorResponseDTO> buscarTodos() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        List<FornecedorResponseDTO> listaFornecedores = new ArrayList<>();
        for (Fornecedor fornecedor : fornecedores) {
            listaFornecedores.add(fornecedorMapper.toDTO(fornecedor));
        }
        return listaFornecedores;
    }

    @Transactional
    public FornecedorResponseDTO criarFornecedor(FornecedorRequestDTO dto){
        Fornecedor novoFornecedor = fornecedorMapper.toEntity(dto);
        novoFornecedor.setIsAtivo(true);
        fornecedorRepository.save(novoFornecedor);
        return fornecedorMapper.toDTO(novoFornecedor);
    }

    @Transactional
    public FornecedorResponseDTO rescindeContrato(UUID id, FornecedorUpdateDTO dto) {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        Fornecedor fornecedorCancelado = fornecedor.get();
        fornecedorCancelado.setIsAtivo(false);
        return fornecedorMapper.toDTO(fornecedorRepository.save(fornecedorCancelado));
    }

    @Transactional
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
