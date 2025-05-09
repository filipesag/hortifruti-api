package com.hortifruti.hortifrutiapi.dto.fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

public record FornecedorRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Length(min = 5, max = 120, message = "O nome deve ter entre 5 a 120 caracteres.")
        String nome,

        @Length(min = 5, max = 120, message = "A cidade deve ter entre 5 a 120 caracteres.")
        @NotBlank(message = "Cidade é obrigatório")
        String cidade,

        @Length(min = 2, max = 20, message = "O estado deve ter entre 5 a 120 caracteres.")
        @NotBlank(message = "Estado é obrigatório")
        String estado,

        @NotBlank(message = "CNPJ é obrigatório")
        @CNPJ
        String cnpj,

        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^\\d{2}\\s\\d{4,5}-\\d{4}$", message = "O telefone deve estar no formato XX XXXXX-XXXX ou XX XXXX-XXXX")
        String telefone,

        @NotBlank(message = "Email é obrigatório")
        @Email
        String email
) {}
