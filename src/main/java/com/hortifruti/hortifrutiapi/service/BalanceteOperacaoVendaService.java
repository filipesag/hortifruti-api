package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.balancete.BalanceteOperacaoMapper;
import com.hortifruti.hortifrutiapi.model.BalanceteOperacaoVenda;
import com.hortifruti.hortifrutiapi.model.FormaPagamento;
import com.hortifruti.hortifrutiapi.model.Venda;
import com.hortifruti.hortifrutiapi.repository.BalanceteOperacaoVendaRepository;
import com.hortifruti.hortifrutiapi.repository.FormaPagamentoRepository;
import com.hortifruti.hortifrutiapi.repository.FormatoVendaRepository;
import com.hortifruti.hortifrutiapi.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BalanceteOperacaoVendaService {

    @Autowired
    private BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    private final BalanceteOperacaoMapper balanceteOperacaoMapper;

    public List<BalanceteOperacaoVenda> buscarTodos() {
        List<BalanceteOperacaoVenda> balanceteOperacaoVendas = balanceteOperacaoVendaRepository.findAll();
        return balanceteOperacaoVendas;
    }

    public BalanceteResponseDTO fechaBalancete(UUID balanceteId, UUID vendaId) {
        BalanceteOperacaoVenda balancete = balanceteOperacaoVendaRepository.findById(balanceteId)
                .orElseThrow(() -> new EntityNotFoundException("Balancete não encontrado"));
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));

        BigDecimal taxa = balancete.getFormaPagamento().getValorTaxa();
        BigDecimal totalVenda = venda.getTotal();
        BigDecimal valorLiquido = calculaValorLiquido(taxa,totalVenda);
        balancete.setValorReceita(valorLiquido);
        return balanceteOperacaoMapper.toResponseDTO(balancete);
    }

    public BalanceteResponseDTO zeraBalancete(UUID balanceteId, UUID vendaId) {
        BalanceteOperacaoVenda balancete = balanceteOperacaoVendaRepository.findById(balanceteId)
                .orElseThrow(() -> new EntityNotFoundException("Balancete não encontrado"));
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));
        balancete.setValorReceita(BigDecimal.ZERO);
        return balanceteOperacaoMapper.toResponseDTO(balancete);
    }

    public BigDecimal calculaValorLiquido(BigDecimal taxa, BigDecimal valorTotal){
        BigDecimal valortaxa = taxa.divide(BigDecimal.valueOf(100)).multiply(valorTotal);
        BigDecimal valorLiquidoFinal = valorTotal.subtract(valortaxa);
        return valorLiquidoFinal;
    }
}
