package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.balancete.BalanceteOperacaoMapper;
import com.hortifruti.hortifrutiapi.model.BalanceteOperacaoVenda;
import com.hortifruti.hortifrutiapi.repository.BalanceteOperacaoVendaRepository;
import com.hortifruti.hortifrutiapi.repository.FormaPagamentoRepository;
import com.hortifruti.hortifrutiapi.model.Venda;
import com.hortifruti.hortifrutiapi.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BalanceteOperacaoVendaService {

    private final BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    private final FormaPagamentoRepository formaPagamentoRepository;

    private final VendaRepository vendaRepository;

    private final BalanceteOperacaoMapper balanceteOperacaoMapper;

    @Transactional
    public List<BalanceteResponseDTO> buscarTodos() {
        List<BalanceteOperacaoVenda> balanceteOperacaoVendas = balanceteOperacaoVendaRepository.findAll();
        List<BalanceteResponseDTO> listaBalancetes = new ArrayList<>();
        for (BalanceteOperacaoVenda balancete : balanceteOperacaoVendas) {
            listaBalancetes.add(balanceteOperacaoMapper.toResponseDTO(balancete));
        }
        return listaBalancetes;
    }

    @Transactional
    public BalanceteOperacaoVenda criaBalancete(BalanceteOperacaoDTO dto){
        BalanceteOperacaoVenda balancete = new BalanceteOperacaoVenda();
        balancete.setDataReceita(dto.dataReceita() != null ? dto.dataReceita() : Instant.now());
        balancete.setValorReceita(dto.valorReceita() != null ? dto.valorReceita() : BigDecimal.ZERO);
        balancete.setFormaPagamento(formaPagamentoRepository.findById(dto.formaPagamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Forma de pagamento não encontrada")));
        return balancete;
    }

    @Transactional
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

    @Transactional
    public BalanceteResponseDTO zeraBalancete(UUID balanceteId, UUID vendaId) {
        BalanceteOperacaoVenda balancete = balanceteOperacaoVendaRepository.findById(balanceteId)
                .orElseThrow(() -> new EntityNotFoundException("Balancete não encontrado"));
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));
        balancete.setValorReceita(BigDecimal.ZERO);
        return balanceteOperacaoMapper.toResponseDTO(balancete);
    }

    @Transactional
    public BigDecimal calculaValorLiquido(BigDecimal taxa, BigDecimal valorTotal){
        BigDecimal valortaxa = taxa.divide(BigDecimal.valueOf(100)).multiply(valorTotal);
        BigDecimal valorLiquidoFinal = valorTotal.subtract(valortaxa);
        return valorLiquidoFinal;
    }
}
