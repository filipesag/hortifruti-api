package com.hortfruit.hortfruitapi.service;

import com.hortfruit.hortfruitapi.model.FormaPagamento;
import com.hortfruit.hortfruitapi.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> buscarTodos() {
        List<FormaPagamento> formasDePagamento = formaPagamentoRepository.findAll();
        return formasDePagamento;
    }
}
