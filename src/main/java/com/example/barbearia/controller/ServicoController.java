package com.example.barbearia.controller;

import com.example.barbearia.dto.request.ServicoRequestDto;
import com.example.barbearia.dto.response.ServicoResponseDto;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import com.example.barbearia.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDto> criarServico(
           @Valid @RequestBody ServicoRequestDto servico){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicoService.criar(servico));
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDto>> listarServicos(){
        return ResponseEntity.ok(servicoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDto> buscarServicoPorId(
            @PathVariable Long id){
        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDto> atualizarServico(
            @PathVariable Long id,
            @Valid @RequestBody ServicoRequestDto servico){
        return ResponseEntity.ok(servicoService.atualizar(id, servico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(
            @PathVariable Long id){
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
