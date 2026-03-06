package com.example.barbearia.controller;


import com.example.barbearia.dto.request.AgendamentoCriarDto;
import com.example.barbearia.dto.request.AgendamentoPatchDto;
import com.example.barbearia.dto.request.AgendamentoRequestDto;
import com.example.barbearia.dto.response.AgendamentoResponseDto;
import com.example.barbearia.model.AgendamentoModel;
import com.example.barbearia.response.ApiSucessResponse;
import com.example.barbearia.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping("/cliente/{clienteId}/servico/{servicoId}")
    public ResponseEntity<ApiSucessResponse<AgendamentoResponseDto>> criar(
            @PathVariable Long clienteId,
            @PathVariable Long servicoId,
            @Valid @RequestBody AgendamentoCriarDto agendamentoCriarDto){
        AgendamentoResponseDto agendamentoSalvo = agendamentoService.criarAgendamento(clienteId, servicoId, agendamentoCriarDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiSucessResponse.success("Agendamento criado com sucesso",  agendamentoSalvo));
    }

    @GetMapping
    public ResponseEntity<ApiSucessResponse<List<AgendamentoResponseDto>>> listarAgendamentos(){
        List<AgendamentoResponseDto> agendamentos =  agendamentoService.listarAgendamentos();
        return ResponseEntity.ok(ApiSucessResponse.success(agendamentos));
    }

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<ApiSucessResponse<List<LocalTime>>> listarHorariosDisponiveis(
            @RequestParam("data") LocalDate data,
            @RequestParam("servicoId") Long servicoId){

        List<LocalTime> horarios = agendamentoService.listarHorariosDisponiveis(data, servicoId);
        return ResponseEntity.ok(ApiSucessResponse.success(horarios));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<AgendamentoResponseDto>> buscarPorId(
            @PathVariable Long id){
        AgendamentoResponseDto agendamento = agendamentoService.buscarAgendamentoPorId(id);
        return ResponseEntity.ok(ApiSucessResponse.success(agendamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<AgendamentoResponseDto>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AgendamentoRequestDto agendamentoRequest){
        AgendamentoResponseDto agendamentoAtualizado = agendamentoService.atualizarAgendamento(id, agendamentoRequest);
        return ResponseEntity.ok(ApiSucessResponse.success("Agendamento atualizado com sucesso",agendamentoAtualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<AgendamentoResponseDto>> patch(
            @PathVariable Long id,
            @RequestBody AgendamentoPatchDto dto){
        AgendamentoResponseDto agendamentoAtualizado = agendamentoService.atualizarParcialmenteAgendamento(id, dto);
        return ResponseEntity.ok(ApiSucessResponse.success("Agendamento atualizado com sucesso", agendamentoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<Void>> deletar(
            @PathVariable Long id){
        agendamentoService.deletar(id);
        return ResponseEntity.ok(ApiSucessResponse.success("Agendamento deletado com sucesso"));
    }

}
