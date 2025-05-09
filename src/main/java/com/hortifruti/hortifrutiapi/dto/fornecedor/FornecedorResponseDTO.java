package com.hortifruti.hortifrutiapi.dto.fornecedor;

import java.util.UUID;

public record FornecedorResponseDTO(
        UUID id,
        String nome,
        String cidade,
        String estado,
        String cnpj,
        String telefone,
        String email
) {}
