package com.hortifruti.hortifrutiapi.service;

import com.hortifruti.hortifrutiapi.model.FormaPagamento;
import com.hortifruti.hortifrutiapi.repository.FormaPagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {


    private final FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public List<FormaPagamento> buscarTodos() {
        List<FormaPagamento> formasDePagamento = formaPagamentoRepository.findAll();
        return formasDePagamento;
    }
}
