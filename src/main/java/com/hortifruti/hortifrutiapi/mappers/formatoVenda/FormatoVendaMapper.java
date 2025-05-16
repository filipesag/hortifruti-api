package com.hortifruti.hortifrutiapi.mappers.formatoVenda;

import com.hortifruti.hortifrutiapi.dto.formatoVenda.FormatoVendaDTO;
import com.hortifruti.hortifrutiapi.model.FormatoVenda;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface FormatoVendaMapper {
    FormatoVendaDTO toDTO(FormatoVenda formato);

    FormatoVenda toEntity(FormatoVendaDTO dto);
}
