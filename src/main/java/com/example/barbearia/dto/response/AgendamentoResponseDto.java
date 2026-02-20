package com.example.barbearia.dto.response;

import com.example.barbearia.enums.Status;
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
public class AgendamentoResponseDto {

    private Long id;
    private Long clienteId;
    private Long servicoId;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Status status;

}
