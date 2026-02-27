package com.example.barbearia.service;

import com.example.barbearia.dto.request.AgendamentoPatchDto;
import com.example.barbearia.dto.request.AgendamentoRequestDto;
import com.example.barbearia.dto.response.AgendamentoResponseDto;
import com.example.barbearia.enums.Status;
import com.example.barbearia.exception.EntidadeNaoEncontradaException;
import com.example.barbearia.exception.HorarioIndisponivelException;
import com.example.barbearia.mapper.AgendamentoMapper;
import com.example.barbearia.model.AgendamentoModel;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import com.example.barbearia.repository.AgendamentoRepository;
import com.example.barbearia.repository.ClienteRepository;
import com.example.barbearia.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ServicoRepository servicoRepository;
    private final ClienteRepository clienteRepository;
    private final AgendamentoMapper  agendamentoMapper;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              ServicoRepository servicoRepository,
                              ClienteRepository clienteRepository, AgendamentoMapper agendamentoMapper) {
        this.agendamentoRepository = agendamentoRepository;
        this.servicoRepository = servicoRepository;
        this.clienteRepository = clienteRepository;
        this.agendamentoMapper = agendamentoMapper;
    }


    @Transactional
    public AgendamentoResponseDto criarAgendamento(Long clienteID, Long servicoID, AgendamentoRequestDto dto) {
        ClienteModel cliente = clienteRepository.findById(clienteID)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não encontrado cliente com esse ID"));
        ServicoModel servico = servicoRepository.findById(servicoID)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não encontrado serviço com esse ID"));

        validar(servico, dto);

        LocalTime inicioNovo = dto.getHoraInicio();
        LocalTime fimNovo = inicioNovo.plusMinutes(servico.getDuracao());

        LocalTime abertura = LocalTime.of(9,0);
        LocalTime fechamento = LocalTime.of(18,0);
        if(inicioNovo.isBefore(abertura) || fimNovo.isAfter(fechamento)){
            throw new HorarioIndisponivelException("Horário fora do expediente");
        }

        List<AgendamentoModel> agendamentosDia = agendamentosMarcados(dto.getData());

        for(AgendamentoModel existente : agendamentosDia){
            if(conflita(inicioNovo, fimNovo, existente.getHoraInicio(), existente.getHoraFim())){
                throw new HorarioIndisponivelException("Horário indisponível");
            }
        }

        AgendamentoModel model = agendamentoMapper.toModel(cliente, servico, dto);
        return agendamentoMapper.toResponseDto(agendamentoRepository.save(model));
    }

    @Transactional(readOnly = true)
    public List<AgendamentoResponseDto> listarAgendamentos(){
        List<AgendamentoModel> agendamentos = agendamentoRepository.findAll();
        return agendamentoMapper.toResponseDTOList(agendamentos) ;
    }

    @Transactional(readOnly = true)
    public AgendamentoResponseDto buscarAgendamentoPorId(Long id){
        AgendamentoModel model = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não encontrado agendamento com esse ID"));
        return agendamentoMapper.toResponseDto(model);
    }

    @Transactional
    public AgendamentoResponseDto atualizarAgendamento(Long id, AgendamentoRequestDto dto) {
        AgendamentoModel model = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não encontrado agendamento com esse ID"));
        ClienteModel cliente = clienteRepository.findById(dto.getClienteId())
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Não encontrado cliente com esse ID"));
        ServicoModel servico = servicoRepository.findById(dto.getServicoId())
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Não encontrado serviço com esse ID"));

        LocalTime horarioNovo = dto.getHoraInicio();
        LocalTime fimHorarioNovo = horarioNovo.plusMinutes(servico.getDuracao());

        LocalTime abertura = LocalTime.of(9, 0);
        LocalTime fechamento = LocalTime.of(18,0);
        if(horarioNovo.isBefore(abertura) || fimHorarioNovo.isAfter(fechamento)){
            throw new HorarioIndisponivelException("Horário fora do expediente");
        }

        List<AgendamentoModel> agendamentosDia = agendamentosMarcados(dto.getData());
        agendamentosDia.removeIf(agendamento -> agendamento.getId().equals(model.getId()));

        for(AgendamentoModel existente : agendamentosDia){
            if(conflita(horarioNovo, fimHorarioNovo, existente.getHoraInicio(), existente.getHoraFim())){
                throw new HorarioIndisponivelException("Horário indisponível");
            }
        }

        agendamentoMapper.updateModel(dto, cliente, servico, model);
        AgendamentoModel save = agendamentoRepository.save(model);
        return agendamentoMapper.toResponseDto(save);
    }

    @Transactional
    public AgendamentoResponseDto atualizarParcialmenteAgendamento(Long id, AgendamentoPatchDto dto) {
        AgendamentoModel model = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não encontrado agendamento com esse ID"));

        // Só busca cliente/servico se vieram no request
        ClienteModel cliente = null;
        ServicoModel servico = null;

        if( dto.getClienteId() != null){
            cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado"));
        }
        if( dto.getServicoId() != null){
            servico = servicoRepository.findById(dto.getServicoId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Serviço não encontrado"));
        }

        LocalTime horarioNovo = dto.getHoraInicio();
        LocalTime fimHorarioNovo = horarioNovo.plusMinutes(servico.getDuracao());

        LocalTime abertura = LocalTime.of(9, 0);
        LocalTime fechamento = LocalTime.of(18, 0);
        if(horarioNovo.isBefore(abertura) || fimHorarioNovo.isAfter(fechamento)){
            throw new HorarioIndisponivelException("Horário fora do expediente");
        }

        List<AgendamentoModel> agendamentosDoDia =  agendamentosMarcados(dto.getData());
        agendamentosDoDia.removeIf(agendamento -> agendamento.getId().equals(model.getId()));

        for(AgendamentoModel existente : agendamentosDoDia){
            if(conflita(horarioNovo, fimHorarioNovo, existente.getHoraInicio(), existente.getHoraFim())){
                throw new HorarioIndisponivelException("Horário indisponível");
            }
        }

        agendamentoMapper.updatePatchModel(dto, cliente, servico, model);
        return agendamentoMapper.toResponseDto(agendamentoRepository.save(model));
    }


    @Transactional
    public void deletar(Long id) {
        if(!agendamentoRepository.existsById(id)){
            throw new EntidadeNaoEncontradaException("ID não encontrado");
        }
        agendamentoRepository.deleteById(id);
    }


    // Metodo para pegar os agendamentos disponíveis do dia
    @Transactional(readOnly = true)
    public List<AgendamentoModel> agendamentosMarcados(LocalDate data) {
        List<Status> status = List.of(Status.MARCADO,Status.CONFIRMADO);
        return agendamentoRepository.findByDataAndStatusInOrderByHoraInicioAsc(data, status);
    }

    // Metodo para listar horarios disponíveis de uma data
    @Transactional(readOnly = true)
    public List<LocalTime> listarHorariosDisponiveis(LocalDate data, Long servicoID) {

        LocalTime abertura = LocalTime.of(9, 0);
        LocalTime fechamento = LocalTime.of(18, 0);

        ServicoModel servico = servicoRepository.findById(servicoID)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não encontrado serviço com esse ID"));

        Duration duracao = Duration.ofMinutes(servico.getDuracao());

        List<AgendamentoModel> agendamentosDia = agendamentosMarcados(data);

        List<LocalTime> horariosDisponiveis = new ArrayList<>();

        Duration intervalo = Duration.ofMinutes(60);

        for(LocalTime horarioAtual = abertura;
                !horarioAtual.plus(duracao).isAfter(fechamento);
                horarioAtual = horarioAtual.plus(intervalo)
        ){

            LocalTime fimHorarioNovo = horarioAtual.plus(duracao);

            boolean disponivel = true;

            for(AgendamentoModel agendamento : agendamentosDia) {

                LocalTime inicioExistente = agendamento.getHoraInicio();
                LocalTime fimExistente = agendamento.getHoraFim();

                if(conflita(horarioAtual, fimHorarioNovo,
                        inicioExistente, fimExistente)) {
                    disponivel = false;
                    break;
                }
            }

            if(disponivel){
                horariosDisponiveis.add(horarioAtual);
            }

        }
        return horariosDisponiveis;
    }




    // Lógica para verificar se agendamento novo conflita com existente
    public static boolean conflita(LocalTime inicioNovo, LocalTime fimNovo,
                                   LocalTime inicioExistente, LocalTime fimExistente){
        return inicioNovo.isBefore(fimExistente) &&
                fimNovo.isAfter(inicioExistente);
    }

    // Lógica para validar data e horario recebidos do DTO e Serviço
    public void validar(ServicoModel servico, AgendamentoRequestDto dto) {
        if (dto.getData() == null || dto.getData().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data inválida ou no passado");
        }
        if (dto.getHoraInicio() == null) {
            throw new IllegalArgumentException("Hora início é obrigatória");
        }
        if (servico == null) {
            throw new IllegalArgumentException("Serviço é obrigatório");
        }
        if (servico.getDuracao() == null || servico.getDuracao() <= 0) {
            throw new IllegalArgumentException("Duração do serviço inválida");
        }

    }
}
