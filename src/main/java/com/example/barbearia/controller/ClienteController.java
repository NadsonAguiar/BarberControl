package com.example.barbearia.controller;

import com.example.barbearia.dto.request.ClienteRequestDto;
import com.example.barbearia.dto.response.ClienteResponseDto;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> criarCliente(
            @Valid @RequestBody ClienteRequestDto cliente){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.criar(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listarClientes(){
        return ResponseEntity.ok(clienteService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarClientePorId(
            @PathVariable Long id){
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDto cliente){
        return ResponseEntity.ok(clienteService.atualizar(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(
            @PathVariable Long id){
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
