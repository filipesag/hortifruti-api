package com.hortifruti.hortifrutiapi.mappers.enderecoEntrega;

import com.hortifruti.hortifrutiapi.dto.enderecoEntrega.EnderecoEntregaDTO;
import com.hortifruti.hortifrutiapi.model.EnderecoEntrega;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoEntregaMapper {
    EnderecoEntregaDTO toDTO(EnderecoEntrega endereco);
}
