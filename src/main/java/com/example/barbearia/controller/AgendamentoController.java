package com.example.barbearia.controller;


import com.example.barbearia.model.AgendamentoModel;
import com.example.barbearia.service.AgendamentoService;
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
    public ResponseEntity<AgendamentoModel> criar(
            @PathVariable Long clienteId,
            @PathVariable Long servicoId,
            @RequestBody AgendamentoModel agendamentoModel){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agendamentoService.criar(clienteId, servicoId, agendamentoModel));
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoModel>> listarAgendamentos(){
        return ResponseEntity.ok(agendamentoService.listar());
    }

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<LocalTime>> listarHorariosDisponiveis(
            @RequestParam("data") LocalDate data,
            @RequestParam("servicoId") Long servicoId){

        List<LocalTime> horarios = agendamentoService.listarHorariosDisponiveis(data, servicoId);
        return ResponseEntity.ok(horarios);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoModel> buscarPorId(
            @PathVariable Long id){
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoModel> atualizar(
            @PathVariable Long id,
            @RequestBody AgendamentoModel agendamentoModel){
        return ResponseEntity.ok(agendamentoService.atualizar(id, agendamentoModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id){
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
