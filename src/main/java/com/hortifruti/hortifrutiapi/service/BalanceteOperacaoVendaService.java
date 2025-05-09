package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.model.BalanceteOperacaoVenda;
import com.hortifruti.hortifrutiapi.repository.BalanceteOperacaoVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BalanceteOperacaoVendaService {

    @Autowired
    private BalanceteOperacaoVendaRepository balanceteOperacaoVendaRepository;

    public List<BalanceteOperacaoVenda> buscarTodos() {
        List<BalanceteOperacaoVenda> balanceteOperacaoVendas = balanceteOperacaoVendaRepository.findAll();
        return balanceteOperacaoVendas;
    }
}
