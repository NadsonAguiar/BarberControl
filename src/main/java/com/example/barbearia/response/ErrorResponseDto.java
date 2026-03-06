package com.example.barbearia.response;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        int status,
        String erro,
        String mensagem,
        LocalDateTime timestamp
) {}
