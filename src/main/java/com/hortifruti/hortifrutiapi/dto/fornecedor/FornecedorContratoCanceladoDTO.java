package com.hortifruti.hortifrutiapi.dto.fornecedor;

public record FornecedorContratoCanceladoDTO(
        String mensagem,
        FornecedorResponseDTO fornecedorUpdateDTO
) {
}
