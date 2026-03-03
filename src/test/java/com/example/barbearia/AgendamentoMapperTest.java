package com.example.barbearia;


import com.example.barbearia.dto.request.AgendamentoCriarDto;
import com.example.barbearia.dto.request.AgendamentoPatchDto;
import com.example.barbearia.dto.request.AgendamentoRequestDto;
import com.example.barbearia.mapper.AgendamentoMapper;
import com.example.barbearia.model.AgendamentoModel;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgendamentoMapperTest {
    private AgendamentoMapper mapper;
    private ClienteModel cliente;
    private ServicoModel servico;

    @BeforeEach
    void setUp() {
        mapper = new AgendamentoMapper();

        cliente = new ClienteModel();
        cliente.setId(1L);

        servico = new ServicoModel();
        servico.setId(1L);
        servico.setDuracao(60); // 60 minutos
    }

    @Test
    @DisplayName("toModel(CriarDto) deve calcular horaFim corretamente")
    void toModel_criarDto_deveCalcularHoraFim() {
        AgendamentoCriarDto dto = new AgendamentoCriarDto();
        dto.setData(LocalDate.now().plusDays(1));
        dto.setHoraInicio(LocalTime.of(9, 0));

        AgendamentoModel model = mapper.toModel(cliente, servico, dto);

        assertEquals(LocalTime.of(10, 0), model.getHoraFim());
    }

    @Test
    @DisplayName("toModel(RequestDto) deve calcular horaFim corretamente")
    void toModel_requestDto_deveCalcularHoraFim() {
        AgendamentoRequestDto dto = new AgendamentoRequestDto();
        dto.setClienteId(1L);
        dto.setServicoId(1L);
        dto.setData(LocalDate.now().plusDays(1));
        dto.setHoraInicio(LocalTime.of(14, 0));

        AgendamentoModel model = mapper.toModel(cliente, servico, dto);

        assertEquals(LocalTime.of(15, 0), model.getHoraFim());
    }

    @Test
    @DisplayName("updateModel deve recalcular horaFim ao atualizar horário")
    void updateModel_deveRecalcularHoraFim() {
        AgendamentoRequestDto dto = new AgendamentoRequestDto();
        dto.setClienteId(1L);
        dto.setServicoId(1L);
        dto.setData(LocalDate.now().plusDays(1));
        dto.setHoraInicio(LocalTime.of(10, 0));

        AgendamentoModel model = new AgendamentoModel();
        model.setHoraFim(LocalTime.of(9, 0)); // horaFim antiga errada

        mapper.updateModel(dto, cliente, servico, model);

        assertEquals(LocalTime.of(11, 0), model.getHoraFim());
    }

    @Test
    @DisplayName("updatePatchModel deve recalcular horaFim quando horaInicio muda")
    void updatePatchModel_deveRecalcularHoraFim_quandoHoraInicioMuda() {
        AgendamentoPatchDto dto = new AgendamentoPatchDto();
        dto.setHoraInicio(LocalTime.of(15, 0));

        AgendamentoModel model = new AgendamentoModel();
        model.setServico(servico);
        model.setHoraInicio(LocalTime.of(9, 0));
        model.setHoraFim(LocalTime.of(10, 0));

        mapper.updatePatchModel(dto, null, null, model);

        assertEquals(LocalTime.of(16, 0), model.getHoraFim());
    }

    @Test
    @DisplayName("updatePatchModel deve recalcular horaFim quando serviço muda")
    void updatePatchModel_deveRecalcularHoraFim_quandoServicoCom90min() {
        ServicoModel novoServico = new ServicoModel();
        novoServico.setId(2L);
        novoServico.setDuracao(90);

        AgendamentoPatchDto dto = new AgendamentoPatchDto();
        dto.setServicoId(2L);

        AgendamentoModel model = new AgendamentoModel();
        model.setServico(servico); // serviço antigo de 60min
        model.setHoraInicio(LocalTime.of(9, 0));
        model.setHoraFim(LocalTime.of(10, 0));

        mapper.updatePatchModel(dto, null, novoServico, model);

        assertEquals(LocalTime.of(10, 30), model.getHoraFim());
    }


}
