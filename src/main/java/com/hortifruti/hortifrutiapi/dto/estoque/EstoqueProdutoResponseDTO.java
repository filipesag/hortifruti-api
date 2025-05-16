package com.hortifruti.hortifrutiapi.dto.estoque;

import java.util.UUID;

public record EstoqueProdutoResponseDTO(
        UUID id,
        UUID produtoId,
        UUID sedeId,
        String nomeSede,
        Integer quantidade
) {}

