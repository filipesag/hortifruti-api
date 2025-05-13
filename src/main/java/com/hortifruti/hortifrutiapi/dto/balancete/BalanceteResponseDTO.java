package com.hortifruti.hortifrutiapi.dto.balancete;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record BalanceteResponseDTO(
        UUID id,
        Instant dataReceita,
        BigDecimal valorReceita,
        UUID formaPagamentoId,
        String formaPagamentoDescricao,
        UUID vendaId
)
{}
