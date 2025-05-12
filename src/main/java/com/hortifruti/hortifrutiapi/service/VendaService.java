package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.formatoVenda.FormatoVendaDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaRequestDTO;
import com.hortifruti.hortifrutiapi.dto.venda.VendaResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.formatoVenda.FormatoVendaMapper;
import com.hortifruti.hortifrutiapi.mappers.venda.VendaMapper;
import com.hortifruti.hortifrutiapi.model.*;
import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;
import com.hortifruti.hortifrutiapi.model.enums.TipoFormatoVenda;
import com.hortifruti.hortifrutiapi.repository.BalanceteOperacaoVendaRepository;
import com.hortifruti.hortifrutiapi.repository.FormatoVendaRepository;
import com.hortifruti.hortifrutiapi.repository.SedeRepository;
import com.hortifruti.hortifrutiapi.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private FormatoVendaRepository formatoVendaRepository;

    @Autowired
    private BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    @Autowired
    private SedeRepository sedeRepository;


    private final VendaMapper vendaMapper;

    private final FormatoVendaMapper formatoVendaMapper;


    public VendaResponseDTO abreVenda(VendaRequestDTO dto) {
        Venda venda = vendaMapper.toEntity(dto);
        venda.setStatusVenda(StatusVenda.ABERTA);
        venda.setDataVenda(dto.dataVenda() != null ? dto.dataVenda() : Date.from(Instant.now()).toInstant());
        venda.setTotal(dto.total() != null && dto.total().compareTo(BigDecimal.ZERO) > 0 ? dto.total() : BigDecimal.ZERO);

        Venda vendaSalvada = vendaRepository.save(venda);
        BalanceteOperacaoVenda balancete = vendaMapper.mapBalancete(dto.balanceteOperacaoVendaId());
        FormatoVenda formatoVenda = formatoVendaRepository.findById(dto.formatoVendaId())
                .orElseThrow(() -> new EntityNotFoundException("Formato de venda não encontrado"));
        venda.setFormatoVenda(formatoVenda);

        if(venda.getFormatoVenda().getTipo() == TipoFormatoVenda.FISICA){
            Sede sede = sedeRepository.findById(venda.getEnderecoEntrega().getId()).orElseThrow(() -> new EntityNotFoundException("Formato de venda não encontrado"));
            venda.setSede(sede);
            venda.setEnderecoEntrega(new EnderecoEntrega(venda.getEnderecoEntrega().getId(),null,null,null,null,null,null));
        }else {
            //cadastrar endereço

        }

        formatoVendaRepository.save(formatoVenda);
        balanceteOperacaoVendaRepository.save(balancete);
        return vendaMapper.toDTO(vendaSalvada);
    }


}
