package com.hortifruti.hortifrutiapi.dto.enderecoEntrega;

import java.util.UUID;

public record EnderecoEntregaDTO(
    UUID id,
    String rua,
    String bairro
){}
