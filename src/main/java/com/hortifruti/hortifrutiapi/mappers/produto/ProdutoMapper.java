package com.hortifruti.hortifrutiapi.mappers.produto;

import com.hortifruti.hortifrutiapi.dto.produto.ProdutoRequestDTO;
import com.hortifruti.hortifrutiapi.dto.produto.ProdutoResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.fornecedor.FornecedorMapper;
import com.hortifruti.hortifrutiapi.model.Fornecedor;
import com.hortifruti.hortifrutiapi.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Mapper(componentModel = "spring", uses = {FornecedorMapper.class})
public interface ProdutoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itensVendidos", ignore = true)
    @Mapping(target = "estoques", ignore = true)
    @Mapping(target = "fornecedor", source = "fornecedorId")
    Produto toEntity(ProdutoRequestDTO dto);

    @Mapping(target = "fornecedor", source = "fornecedor")
    ProdutoResponseDTO toDTO(Produto produto);

    default Fornecedor map(UUID id) {
        if (id == null) {
            return null;
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        return fornecedor;
    }
}
