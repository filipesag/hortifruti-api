package com.hortifruti.hortifrutiapi.dto.venda;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemVendaDTO(
        UUID id,
        UUID produtoId,
        String nomeProduto,
        Double quantidade,
        BigDecimal precoUnitario
) {}
