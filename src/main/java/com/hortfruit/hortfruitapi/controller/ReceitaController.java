package com.hortfruit.hortfruitapi.controller;

import com.hortfruit.hortfruitapi.model.Receita;
import com.hortfruit.hortfruitapi.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
    @Autowired
    private ReceitaService receitaService;

    @GetMapping
    public List<Receita> buscarTodos(){
        List<Receita> receitas = receitaService.buscarTodos();
        return receitas;
    }
}
