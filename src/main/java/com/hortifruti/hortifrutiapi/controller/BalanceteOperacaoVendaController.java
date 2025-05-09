package com.hortifruti.hortifrutiapi.controller;

import com.hortifruti.hortifrutiapi.model.BalanceteOperacaoVenda;
import com.hortifruti.hortifrutiapi.service.BalanceteOperacaoVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/receitas")
public class BalanceteOperacaoVendaController {
    @Autowired
    private BalanceteOperacaoVendaService balanceteOperacaoVendaService;

    @GetMapping
    public List<BalanceteOperacaoVenda> buscarTodos(){
        List<BalanceteOperacaoVenda> balanceteOperacaoVendas = balanceteOperacaoVendaService.buscarTodos();
        return balanceteOperacaoVendas;
    }
}
