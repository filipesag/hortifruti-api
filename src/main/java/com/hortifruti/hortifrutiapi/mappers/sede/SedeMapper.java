package com.hortifruti.hortifrutiapi.mappers.sede;

import com.hortifruti.hortifrutiapi.dto.sede.SedeRequestDTO;
import com.hortifruti.hortifrutiapi.dto.sede.SedeResponseDTO;
import com.hortifruti.hortifrutiapi.model.Sede;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface SedeMapper {
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "bairro", source = "bairro")
    @Mapping(target = "cidade", source = "cidade")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "rua", source = "rua")
    @Mapping(target = "numero", source = "numero")
    @Mapping(target = "descricao", source = "descricao")
    Sede toEntity(SedeRequestDTO dto);

    SedeResponseDTO toDTO(Sede sede);
}

