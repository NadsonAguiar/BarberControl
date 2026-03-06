package com.example.barbearia.controller;

import com.example.barbearia.dto.request.ServicoRequestDto;
import com.example.barbearia.dto.response.ServicoResponseDto;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import com.example.barbearia.response.ApiSucessResponse;
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
    public ResponseEntity<ApiSucessResponse<ServicoResponseDto>> criarServico(
           @Valid @RequestBody ServicoRequestDto servico){
        ServicoResponseDto servicoSalvo = servicoService.criar(servico);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiSucessResponse.success("Serviço salvo com sucesso", servicoSalvo));
    }

    @GetMapping
    public ResponseEntity<ApiSucessResponse<List<ServicoResponseDto>>> listarServicos(){
        List<ServicoResponseDto> servicos = servicoService.listar();
        return ResponseEntity.ok(ApiSucessResponse.success(servicos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<ServicoResponseDto>> buscarServicoPorId(
            @PathVariable Long id){
        ServicoResponseDto servico = servicoService.buscarPorId(id);
        return ResponseEntity.ok(ApiSucessResponse.success(servico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<ServicoResponseDto>> atualizarServico(
            @PathVariable Long id,
            @Valid @RequestBody ServicoRequestDto servico){
        ServicoResponseDto servicoAtualizado =  servicoService.atualizar(id, servico);
        return ResponseEntity.ok(ApiSucessResponse.success("Serviço atualizado com sucesso",  servicoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<Void>> deletarServico(
            @PathVariable Long id){
        servicoService.deletar(id);
        return ResponseEntity.ok(ApiSucessResponse.success("Serviço deletado com sucesso"));
    }
}
