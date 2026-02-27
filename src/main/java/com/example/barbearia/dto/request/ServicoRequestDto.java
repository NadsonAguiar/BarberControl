package com.example.barbearia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServicoRequestDto(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotNull(message = "Duração é obrigatório")
        Integer duracao,
        @NotNull(message = "Preço é obrigatório")
        Double preco
) {
}
