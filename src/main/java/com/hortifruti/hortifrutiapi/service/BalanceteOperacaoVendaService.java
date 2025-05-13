package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteOperacaoDTO;
import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteResponseDTO;
import com.hortifruti.hortifrutiapi.mappers.balancete.BalanceteOperacaoMapper;
import com.hortifruti.hortifrutiapi.model.BalanceteOperacaoVenda;
import com.hortifruti.hortifrutiapi.model.FormaPagamento;
import com.hortifruti.hortifrutiapi.repository.BalanceteOperacaoVendaRepository;
import com.hortifruti.hortifrutiapi.repository.FormaPagamentoRepository;
import com.hortifruti.hortifrutiapi.repository.FormatoVendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceteOperacaoVendaService {

    @Autowired
    private BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    private final BalanceteOperacaoMapper balanceteOperacaoMapper;

    public List<BalanceteOperacaoVenda> buscarTodos() {
        List<BalanceteOperacaoVenda> balanceteOperacaoVendas = balanceteOperacaoVendaRepository.findAll();
        return balanceteOperacaoVendas;
    }

    public BalanceteResponseDTO criaBalancete(BalanceteOperacaoDTO dto){
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(dto.formaPagamentoId())
                .orElseThrow(()-> new EntityNotFoundException("Forma de pagamento não encontrado"));
        BalanceteOperacaoVenda balancete = balanceteOperacaoMapper.toEntity(dto,formaPagamento,null);
        balanceteOperacaoVendaRepository.save(balancete);
        return balanceteOperacaoMapper.toResponseDTO(balancete);
    }
}
