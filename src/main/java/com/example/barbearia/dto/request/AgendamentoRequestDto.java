package com.example.barbearia.dto.request;

import com.example.barbearia.enums.Status;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
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
public class AgendamentoRequestDto {

    private Long clienteId;
    private Long servicoId;
    private LocalDate data;
    private LocalTime horaInicio;
    private Status status;

}
