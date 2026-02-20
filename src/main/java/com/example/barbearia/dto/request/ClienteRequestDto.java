package com.example.barbearia.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDto(
        @NotBlank(message = "Nome obrigatório")
        String nome,
        @NotBlank(message = "Telefone obrigatório")
        String telefone
) {
}
