package com.hortifruti.hortifrutiapi.dto.sede;

import java.util.UUID;

public record SedeResponseDTO (
    UUID id,
    String nome,
    String bairro,
    String cidade,
    String estado,
    String rua,
    String numero,
    String descricao
) {}
