package com.example.barbearia;

import com.example.barbearia.mapper.AgendamentoMapper;
import com.example.barbearia.model.AgendamentoModel;
import com.example.barbearia.model.ServicoModel;
import com.example.barbearia.repository.AgendamentoRepository;
import com.example.barbearia.repository.ClienteRepository;
import com.example.barbearia.repository.ServicoRepository;
import com.example.barbearia.service.AgendamentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;
    @Mock
    private ServicoRepository servicoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private AgendamentoMapper agendamentoMapper;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Test
    @DisplayName("Deve retornar true quando os horários se sobrepõem no meio")
    void conflita_deveRetornarTrue_quandoHorarios_Sobrepoem(){
        assertTrue(AgendamentoService.conflita(
                LocalTime.of(9,0), LocalTime.of(11,0),
                LocalTime.of(9,30), LocalTime.of(10,30)
        ));
    }

    @Test
    @DisplayName("Deve retornar true quando novo horário contém o existente inteiro")
    void conflita_deveRetornarTrue_quandoNovoContemExistente(){
        assertTrue(AgendamentoService.conflita(
                LocalTime.of(9,0), LocalTime.of(11,0),
                LocalTime.of(9,30), LocalTime.of(10,30)
        ));
    }

    @Test
    @DisplayName("Deve retornar false quando horário novo começa exatamente quando o existente termina")
    void conflita_deveRetornarFalse_quandoNovoComeca_AposExistenteTerminar() {
        assertFalse(AgendamentoService.conflita(
                LocalTime.of(10, 0), LocalTime.of(11, 0),
                LocalTime.of(9, 0), LocalTime.of(10, 0)
        ));
    }

    @Test
    @DisplayName("Deve retornar false quando horários são sequenciais sem sobreposição")
    void conflita_deveRetornarFalse_quandoHorariosSequenciais() {
        assertFalse(AgendamentoService.conflita(
                LocalTime.of(11, 0), LocalTime.of(12, 0),
                LocalTime.of(9, 0), LocalTime.of(10, 0)
        ));
    }

    @Test
    @DisplayName("Deve retornar true quando novo horário começa antes e termina no meio do existente")
    void conflita_deveRetornarTrue_quandoNovoTerminaNomeio() {
        assertTrue(AgendamentoService.conflita(
                LocalTime.of(8, 30), LocalTime.of(9, 30),
                LocalTime.of(9, 0), LocalTime.of(10, 0)
        ));
    }
    // ===================== listarHorariosDisponiveis() ===================== //
    @Test
    @DisplayName("Deve retornar todos os horários quando não há agendamentos no dia")
    void listarHorariosDisponiveis_deveRetornarTodosHorarios_quandoDiaVazio() {
        ServicoModel servico = new ServicoModel();
        servico.setId(1L);
        servico.setDuracao(60);

        when(servicoRepository.findById(1L)).thenReturn(java.util.Optional.of(servico));
        when(agendamentoRepository.findByDataAndStatusInOrderByHoraInicioAsc(
                any(), any())).thenReturn(List.of());

        List<LocalTime> horarios = agendamentoService.listarHorariosDisponiveis(
                LocalDate.now().plusDays(1), 1L);

        // Expediente 09:00 às 18:00 com serviço de 60min = 9 slots
        assertEquals(9, horarios.size());
        assertEquals(LocalTime.of(9, 0), horarios.get(0));
        assertEquals(LocalTime.of(17, 0), horarios.get(horarios.size() - 1  ));
    }

    @Test
    @DisplayName("Deve remover horário ocupado da listagem")
    void listarHorariosDisponiveis_deveRemoverHorarioOcupado() {
        ServicoModel servico = new ServicoModel();
        servico.setId(1L);
        servico.setDuracao(60);

        AgendamentoModel agendamentoExistente = new AgendamentoModel();
        agendamentoExistente.setHoraInicio(LocalTime.of(9, 0));
        agendamentoExistente.setHoraFim(LocalTime.of(10, 0));

        when(servicoRepository.findById(1L)).thenReturn(java.util.Optional.of(servico));
        when(agendamentoRepository.findByDataAndStatusInOrderByHoraInicioAsc(
                any(), any())).thenReturn(List.of(agendamentoExistente));

        List<LocalTime> horarios = agendamentoService.listarHorariosDisponiveis(
                LocalDate.now().plusDays(1), 1L);

        assertFalse(horarios.contains(LocalTime.of(9, 0)));
        assertTrue(horarios.contains(LocalTime.of(10, 0)));
    }

    @Test
    @DisplayName("Deve lançar exceção quando serviço não existe")
    void listarHorariosDisponiveis_deveLancarExcecao_quandoServicoNaoExiste() {
        when(servicoRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(
                com.example.barbearia.exception.EntidadeNaoEncontradaException.class,
                () -> agendamentoService.listarHorariosDisponiveis(LocalDate.now().plusDays(1), 99L)
        );
    }


}
