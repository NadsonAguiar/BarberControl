package com.example.barbearia.dto.request;

import com.example.barbearia.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoPatchDto {

    private Long clienteId;      // null = não alterar
    private Long servicoId;      // null = não alterar
    private LocalDate data;      // null = não alterar
    private LocalTime horaInicio; // null = não alterar
    private Status status;       // null = não alterar

}
