package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.balancete.BalanceteOperacaoMapper;
import com.hortifruti.hortifrutiapi.model.BalanceteOperacaoVenda;
import com.hortifruti.hortifrutiapi.repository.BalanceteOperacaoVendaRepository;
import com.hortifruti.hortifrutiapi.repository.FormaPagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceteOperacaoVendaService {

    private final BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    private final FormaPagamentoRepository formaPagamentoRepository;

    private final BalanceteOperacaoMapper balanceteOperacaoMapper;

    public List<BalanceteResponseDTO> buscarTodos() {
        List<BalanceteOperacaoVenda> balanceteOperacaoVendas = balanceteOperacaoVendaRepository.findAll();
        List<BalanceteResponseDTO> listaBalancetes = new ArrayList<>();
        for (BalanceteOperacaoVenda balancete : balanceteOperacaoVendas) {
            listaBalancetes.add(balanceteOperacaoMapper.toResponseDTO(balancete));
        }
        return listaBalancetes;
    }

    public BalanceteOperacaoVenda criaBalancete(BalanceteOperacaoDTO dto){
        BalanceteOperacaoVenda balancete = new BalanceteOperacaoVenda();
        balancete.setDataReceita(dto.dataReceita() != null ? dto.dataReceita() : Instant.now());
        balancete.setValorReceita(dto.valorReceita() != null ? dto.valorReceita() : BigDecimal.ZERO);
        balancete.setFormaPagamento(formaPagamentoRepository.findById(dto.formaPagamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Forma de pagamento não encontrada")));
        return balancete;
    }
}
