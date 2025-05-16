package com.hortifruti.hortifrutiapi.dto.formatoVenda;

import com.hortifruti.hortifrutiapi.model.enums.TipoFormatoVenda;

import java.util.UUID;

public record FormatoVendaDTO(
        UUID id,
        TipoFormatoVenda tipo
) {}
