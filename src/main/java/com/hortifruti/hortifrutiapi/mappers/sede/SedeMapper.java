package com.hortifruti.hortifrutiapi.mappers.sede;

import com.hortifruti.hortifrutiapi.dto.sede.SedeRequestDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.model.Sede;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SedeMapper {

    Sede toEntity(SedeRequestDTO dto);

    SedeResponseDTO toDTO(Sede sede);
}
