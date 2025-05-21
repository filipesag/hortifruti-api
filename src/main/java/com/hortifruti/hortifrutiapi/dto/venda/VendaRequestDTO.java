package com.hortifruti.hortifrutiapi.dto.venda;

import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;
import java.util.UUID;
import java.math.BigDecimal;


public record VendaRequestDTO(
    BigDecimal total,
    StatusVenda statusVenda,
    UUID sedeId,
    UUID formatoVendaId
) {}
