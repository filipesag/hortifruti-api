package com.hortifruti.hortifrutiapi.mappers.formatoVenda;

import com.hortifruti.hortifrutiapi.dto.formatoVenda.FormatoVendaDTO;
import com.hortifruti.hortifrutiapi.model.FormatoVenda;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FormatoVendaMapper {
    FormatoVendaDTO toDTO(FormatoVenda formato);

    FormatoVenda toEntity(FormatoVendaDTO dto);
}
