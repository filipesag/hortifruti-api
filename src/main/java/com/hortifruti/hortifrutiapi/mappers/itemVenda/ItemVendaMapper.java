package com.hortifruti.hortifrutiapi.mappers.itemVenda;

import com.hortifruti.hortifrutiapi.dto.venda.ItemVendaDTO;
import com.hortifruti.hortifrutiapi.model.ItemVenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemVendaMapper {

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "nomeProduto", source = "produto.nome")
    ItemVendaDTO toDTO(ItemVenda item);
}
