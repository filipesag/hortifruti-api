package com.hortifruti.hortifrutiapi.dto.venda;

import com.hortifruti.hortifrutiapi.model.enums.StatusVenda;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface VendasPorSedeDTO {
    UUID getId();
    Instant getDataVenda();
    BigDecimal getTotal();
    StatusVenda getStatusVenda();
    String getNome();

}
