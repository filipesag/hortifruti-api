package com.hortifruti.hortifrutiapi.mappers.venda;

import com.hortifruti.hortifrutiapi.dto.venda.VendaRequestDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.enderecoEntrega.EnderecoEntregaMapper;
import com.hortifruti.hortifrutiapi.mappers.formatoVenda.FormatoVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.itemVenda.ItemVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.sede.SedeMapper;
import com.hortifruti.hortifrutiapi.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {SedeMapper.class, EnderecoEntregaMapper.class, FormatoVendaMapper.class, ItemVendaMapper.class})
public interface VendaMapper {

    @Mapping(target = "balanceteOperacaoVenda", source = "balanceteOperacaoVendaId")
    @Mapping(target = "sede", source = "sedeId")
    @Mapping(target = "enderecoEntrega", source = "enderecoEntregaId")
    @Mapping(target = "formatoVenda", source = "formatoVendaId")
    Venda toEntity(VendaRequestDTO dto);

    VendaResponseDTO toDTO(Venda venda);

    default Sede mapSede(UUID id) {
        if (id == null) return null;
        Sede sede = new Sede();
        sede.setId(id);
        return sede;
    }

    default EnderecoEntrega mapEndereco(UUID id) {
        if (id == null) return null;
        EnderecoEntrega endereco = new EnderecoEntrega();
        endereco.setId(id);
        return endereco;
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
