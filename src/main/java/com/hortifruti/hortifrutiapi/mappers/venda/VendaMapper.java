package com.hortifruti.hortifrutiapi.mappers.venda;

import com.hortifruti.hortifrutiapi.dto.venda.VendaRequestDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.formatoVenda.FormatoVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.itemVenda.ItemVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.sede.SedeMapper;
import com.hortifruti.hortifrutiapi.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Mapper(componentModel = "spring", uses = {SedeMapper.class,FormatoVendaMapper.class, ItemVendaMapper.class})
public interface VendaMapper {

    @Mapping(target = "sede", source = "sedeId")
    @Mapping(target = "tipo_venda", source = "formatoVendaId")
    Venda toEntity(VendaRequestDTO dto);

    VendaResponseDTO toDTO(Venda venda);

    default Sede mapSede(UUID id) {
        if (id == null) return null;
        Sede sede = new Sede();
        sede.setId(id);
        return sede;
    }

    default FormatoVenda mapFormato(UUID id) {
        if (id == null) return null;
        FormatoVenda formato = new FormatoVenda();
        formato.setId(id);
        return formato;
    }

    default BalanceteOperacaoVenda mapBalancete(UUID id) {
        if (id == null) return null;
        BalanceteOperacaoVenda b = new BalanceteOperacaoVenda();
        b.setId(id);
        return b;
    }
}
