package com.hortifruti.hortifrutiapi.dto.fornecedor;


public record FornecedorUpdateDTO(
        String nome,
        String cidade,
        String estado,
        String cnpj,
        String telefone,
        String email,
        Boolean isAtivo
) {}
