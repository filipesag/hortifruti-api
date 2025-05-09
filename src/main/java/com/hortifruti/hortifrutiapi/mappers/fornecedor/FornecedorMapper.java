package com.hortifruti.hortifrutiapi.mappers.fornecedor;

import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorRequestDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorResponseDTO;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {

    Fornecedor toEntity(FornecedorRequestDTO dto);

    FornecedorResponseDTO toDTO(Fornecedor fornecedor);
}
