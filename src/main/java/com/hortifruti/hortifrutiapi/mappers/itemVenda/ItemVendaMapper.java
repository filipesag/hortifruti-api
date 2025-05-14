package com.hortifruti.hortifrutiapi.mappers.itemVenda;

import com.hortifruti.hortifrutiapi.dto.venda.ItemVendaAdicionadoDTO;
import com.hortifruti.hortifrutiapi.dto.venda.ItemVendaDTO;
import com.hortifruti.hortifrutiapi.model.ItemVenda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper(componentModel = "spring")
public interface ItemVendaMapper {
    @Mapping(target = "venda", ignore = true)
    @Mapping(target = "produto", ignore = true)
    ItemVenda toEntity(ItemVendaDTO dto);

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "nomeProduto", source = "produto.nome")
    @Mapping(target = "precoUnitario", source = "precoUnit")
    ItemVendaDTO toDTO(ItemVenda item);

    List<ItemVendaDTO> toDTOList(List<ItemVenda> itens);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venda", ignore = true)
    @Mapping(target = "produto", ignore = true)
    ItemVenda toEntity(ItemVendaAdicionadoDTO dto);
}
