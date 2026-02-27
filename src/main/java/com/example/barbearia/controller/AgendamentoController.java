package com.example.barbearia.controller;


import com.example.barbearia.dto.request.AgendamentoPatchDto;
import com.example.barbearia.dto.request.AgendamentoRequestDto;
import com.example.barbearia.dto.response.AgendamentoResponseDto;
import com.example.barbearia.model.AgendamentoModel;
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
    public ResponseEntity<AgendamentoResponseDto> criar(
            @PathVariable Long clienteId,
            @PathVariable Long servicoId,
            @Valid @RequestBody AgendamentoRequestDto agendamentoRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agendamentoService.criarAgendamento(clienteId, servicoId, agendamentoRequest));
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDto>> listarAgendamentos(){
        return ResponseEntity.ok(agendamentoService.listarAgendamentos());
    }

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<LocalTime>> listarHorariosDisponiveis(
            @RequestParam("data") LocalDate data,
            @RequestParam("servicoId") Long servicoId){

        List<LocalTime> horarios = agendamentoService.listarHorariosDisponiveis(data, servicoId);
        return ResponseEntity.ok(horarios);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> buscarPorId(
            @PathVariable Long id){
        return ResponseEntity.ok(agendamentoService.buscarAgendamentoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AgendamentoRequestDto agendamentoRequest){
        return ResponseEntity.ok(agendamentoService.atualizarAgendamento(id, agendamentoRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> patch(
            @PathVariable Long id,
            @RequestBody AgendamentoPatchDto dto
    ){
        return ResponseEntity.ok(agendamentoService.atualizarParcialmenteAgendamento(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id){
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
