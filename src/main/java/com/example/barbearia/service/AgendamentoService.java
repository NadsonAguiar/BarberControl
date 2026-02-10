package com.example.barbearia.service;

import com.example.barbearia.enums.Status;
import com.example.barbearia.model.AgendamentoModel;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import com.example.barbearia.repository.AgendamentoRepository;
import com.example.barbearia.repository.ClienteRepository;
import com.example.barbearia.repository.ServicoRepository;
import org.springframework.stereotype.Service;

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

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              ServicoRepository servicoRepository,
                              ClienteRepository clienteRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.servicoRepository = servicoRepository;
        this.clienteRepository = clienteRepository;
    }
    public AgendamentoModel criar(Long clienteID, Long servicoID, AgendamentoModel agendamento) {
        ClienteModel cliente = clienteRepository.findById(clienteID)
                .orElseThrow(() -> new RuntimeException("Não encontrado cliente com esse ID"));
        ServicoModel servico = servicoRepository.findById(servicoID)
                .orElseThrow(() -> new RuntimeException("Não encontrado serviço com esse ID"));
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        return agendamentoRepository.save(agendamento);
    }

    public List<AgendamentoModel> listar(){
        return agendamentoRepository.findAll();
    }

    public AgendamentoModel buscarPorId(Long id){
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado agendamento com esse ID"));
    }

    public AgendamentoModel atualizar(Long id, AgendamentoModel agendamentoModel) {
        AgendamentoModel servico = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado agendamento com esse ID"));
        servico.setId(id);
        return agendamentoRepository.save(agendamentoModel);
    }

    public void deletar(Long id) {
        agendamentoRepository.deleteById(id);
    }



    public List<LocalTime> listarHorariosDisponiveis(LocalDate data, Long servicoID) {

        LocalTime abertura = LocalTime.of(9, 0);
        LocalTime fechamento = LocalTime.of(18, 0);

        ServicoModel servico = servicoRepository.findById(servicoID)
                .orElseThrow(() -> new RuntimeException("Não encontrado serviço com esse ID"));

        Duration duracao = Duration.ofMinutes(servico.getDuracao());
        List<AgendamentoModel> agendamentosDia = agendamentosDisponiveis(data);

        List<LocalTime> horariosDisponiveis = new ArrayList<>();

        Duration intervalo = Duration.ofMinutes(60);

        for(LocalTime horarioAtual = abertura;
                !horarioAtual.plus(duracao).isAfter(fechamento);
                horarioAtual = horarioAtual.plus(intervalo)
        ){

            LocalTime inicioHorarioNovo = horarioAtual;
            LocalTime fimHorarioNovo = horarioAtual.plus(duracao);

            boolean disponivel = true;

            for(AgendamentoModel agendamento : agendamentosDia) {

                LocalTime inicioExistente = agendamento.getHoraInicio();
                LocalTime fimExistente = agendamento.getHoraFim();

                if(conflita(inicioHorarioNovo, fimHorarioNovo,
                        inicioExistente, fimExistente)) {
                    disponivel = false;
                    break;
                }
            }

            if(disponivel){
                horariosDisponiveis.add(inicioHorarioNovo);
            }

        }
        return horariosDisponiveis;
    }


    private boolean conflita(LocalTime inicioNovo, LocalTime fimNovo,
                            LocalTime inicioExistente, LocalTime fimExistente) {
        return inicioNovo.isBefore(fimExistente) &&
                fimNovo.isAfter(inicioExistente);
    }


    public List<AgendamentoModel> agendamentosDisponiveis(LocalDate data) {
        List<Status> status = List.of(Status.MARCADO,Status.CONFIRMADO);
        return agendamentoRepository.findByDataAndStatusInOrderByHoraInicioAsc(data, status);
    }

    public boolean dataValida(LocalDate data){
        return !data.isBefore(LocalDate.now());
    }
}
