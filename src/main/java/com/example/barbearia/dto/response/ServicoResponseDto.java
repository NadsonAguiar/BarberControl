package com.example.barbearia.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServicoResponseDto (
        Long id,
        String nome,
        Integer duracao,
        Double preco
){
}
