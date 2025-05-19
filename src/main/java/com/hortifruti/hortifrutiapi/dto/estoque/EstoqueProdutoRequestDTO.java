package com.hortifruti.hortifrutiapi.dto.estoque;

import java.util.UUID;

public record EstoqueProdutoRequestDTO(
        UUID produtoId,
        UUID sedeId,
        Double quantidade
) {}
