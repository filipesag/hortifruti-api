package com.hortifruti.hortifrutiapi.dto.balancete;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record BalanceteOperacaoDTO(
        Instant dataReceita,
        BigDecimal valorReceita,
        UUID formaPagamentoId
) {}
