package com.example.barbearia.dto.request;

import com.example.barbearia.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Usado na criação — IDs vêm pela URL, não pelo body
public class AgendamentoCriarDto {
    @NotNull(message = "Data é obrigatório")
    private LocalDate data;
    @NotNull(message = "Hora de inicio é obrigatório")
    private LocalTime horaInicio;
    private Status status;
}
