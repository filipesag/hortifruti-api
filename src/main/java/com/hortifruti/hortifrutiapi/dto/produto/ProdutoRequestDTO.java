package com.hortifruti.hortifrutiapi.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Length(min = 5, max = 120, message = "O nome deve ter entre 5 a 120 caracteres.")
        String nome,

        @NotBlank(message = "Unidade de medida é obrigatório")
        String unidadeMedida,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal preco,

        @NotNull(message = "ID do fornecedor é obrigatório")
        UUID fornecedorId
){}
