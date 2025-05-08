package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.model.Receita;
import com.hortifruti.hortifrutiapi.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    public List<Receita> buscarTodos() {
        List<Receita> receitas = receitaRepository.findAll();
        return receitas;
    }
}
