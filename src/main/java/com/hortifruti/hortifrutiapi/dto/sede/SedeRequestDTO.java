package com.hortifruti.hortifrutiapi.dto.sede;


import jakarta.validation.constraints.NotBlank;

public record SedeRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Bairro é obrigatório")
        String bairro,
        @NotBlank(message = "cidade é obrigatório")
        String cidade,
        @NotBlank(message = "Nome é obrigatório")
        String estado,
        @NotBlank(message = "estado é obrigatório")
        String rua,
        @NotBlank(message = "numero é obrigatório")
        String numero,
        String descricao
) {}
