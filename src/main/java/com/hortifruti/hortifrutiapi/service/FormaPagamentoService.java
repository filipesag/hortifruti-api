package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.model.FormaPagamento;
import com.hortifruti.hortifrutiapi.repository.FormaPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {


    private final FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> buscarTodos() {
        List<FormaPagamento> formasDePagamento = formaPagamentoRepository.findAll();
        return formasDePagamento;
    }
}
