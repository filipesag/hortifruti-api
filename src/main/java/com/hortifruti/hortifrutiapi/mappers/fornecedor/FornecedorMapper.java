package com.hortifruti.hortifrutiapi.mappers.fornecedor;

import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorRequestDTO;
import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorResponseDTO;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cidade", source = "cidade")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "cnpj", source = "cnpj")
    @Mapping(target = "telefone", source = "telefone")
    Fornecedor toEntity(FornecedorRequestDTO dto);

    FornecedorResponseDTO toDTO(Fornecedor fornecedor);
}
