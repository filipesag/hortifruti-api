package com.hortifruti.hortifrutiapi.dto.venda;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemVendaAdicionadoDTO(
        UUID produtoId,
        Double quantidade,
        BigDecimal precoUnitario
) {}
