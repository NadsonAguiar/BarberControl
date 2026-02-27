package com.example.barbearia.dto.request;

import com.example.barbearia.enums.Status;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;
    @NotNull(message = "Serviço é obrigatório")
    private Long servicoId;
    @NotNull(message = "Data é obrigatório")
    private LocalDate data;
    @NotNull(message = "Hora de inicio é obrigatório")
    private LocalTime horaInicio;
    private Status status;

}
