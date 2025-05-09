package com.hortifruti.hortifrutiapi.dto.produto;

import com.hortifruti.hortifrutiapi.dto.fornecedor.FornecedorResponseDTO;
import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoResponseDTO(
    UUID id,
    String nome,
    String unidadeMedida,
    BigDecimal preco,
    FornecedorResponseDTO fornecedor
){}
