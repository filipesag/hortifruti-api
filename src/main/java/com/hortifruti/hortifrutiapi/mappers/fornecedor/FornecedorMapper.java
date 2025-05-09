package com.hortifruti.hortifrutiapi.mappers.fornecedor;

import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorRequestDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorResponseDTO;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {

    default Fornecedor toEntity(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setCidade(dto.cidade());
        fornecedor.setEstado(dto.estado());
        fornecedor.setCnpj(dto.cnpj());
        fornecedor.setTelefone(dto.telefone());
        fornecedor.setEmail(dto.email());
        fornecedor.setIsAtivo(true);
        return fornecedor;
    }

    FornecedorResponseDTO toDTO(Fornecedor fornecedor);
}
