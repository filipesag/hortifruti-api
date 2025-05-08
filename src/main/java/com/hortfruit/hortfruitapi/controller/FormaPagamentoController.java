package com.hortfruit.hortfruitapi.controller;

import com.hortfruit.hortfruitapi.model.FormaPagamento;
import com.hortfruit.hortfruitapi.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @GetMapping
    public List<FormaPagamento> buscarTodos(){
        List<FormaPagamento> formasDePagamento = formaPagamentoService.buscarTodos();
        return formasDePagamento;
    }
}
