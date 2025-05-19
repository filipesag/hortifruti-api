package com.hortifruti.hortifrutiapi.dto.estoque;

import java.util.UUID;

public record EstoqueProdutoResponseDTO(
        UUID id,
        UUID produtoId,
        UUID sedeId,
        String nomeSede,
        @jakarta.validation.constraints.NotNull @jakarta.validation.constraints.Min(0) Double quantidade
) {}

