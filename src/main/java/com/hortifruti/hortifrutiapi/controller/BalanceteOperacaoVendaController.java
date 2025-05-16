package com.hortifruti.hortifrutiapi.controller;

import com.hortifruti.hortifrutiapi.dto.balancete.BalanceteResponseDTO;
import com.hortifruti.hortifrutiapi.service.BalanceteOperacaoVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/balancete")
public class BalanceteOperacaoVendaController {

    @Autowired
    private BalanceteOperacaoVendaService balanceteOperacaoVendaService;

    @GetMapping("/busca-balancetes")
    public List<BalanceteResponseDTO> buscarTodos(){
        List<BalanceteResponseDTO> balanceteOperacaoVendas = balanceteOperacaoVendaService.buscarTodos();
        return balanceteOperacaoVendas;
    }
}
