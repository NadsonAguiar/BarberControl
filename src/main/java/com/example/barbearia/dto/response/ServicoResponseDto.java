package com.example.barbearia.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServicoResponseDto (
        Long id,
        String nome,
        Integer duracao,
        BigDecimal preco
){
}
