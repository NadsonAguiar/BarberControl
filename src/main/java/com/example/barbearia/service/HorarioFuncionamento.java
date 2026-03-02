package com.example.barbearia.service;

import org.springframework.stereotype.Component;

import java.time.LocalTime;

public final class HorarioFuncionamento {

    private HorarioFuncionamento() {}

    public static final LocalTime HORARIO_ABERTURA = LocalTime.of(9,0);
    public static final LocalTime HORARIO_ENCERRAMENTO = LocalTime.of(18,0);


}
