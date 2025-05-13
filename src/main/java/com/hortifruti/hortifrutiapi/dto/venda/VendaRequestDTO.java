package com.hortifruti.hortifrutiapi.dto.venda;

import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;

import java.time.Instant;
import java.util.UUID;
import java.math.BigDecimal;


public record VendaRequestDTO(
    Instant dataVenda,
    BigDecimal total,
    StatusVenda statusVenda,
    UUID sedeId,
    UUID formatoVendaId
) {}
