package com.example.barbearia.controller;

import com.example.barbearia.dto.request.ClienteRequestDto;
import com.example.barbearia.dto.response.ClienteResponseDto;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.response.ApiSucessResponse;
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
    public ResponseEntity<ApiSucessResponse<ClienteResponseDto>> criarCliente(
            @Valid @RequestBody ClienteRequestDto cliente){
        ClienteResponseDto clienteSalvo = clienteService.criar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiSucessResponse.success("Cliente criado com sucesso", clienteSalvo));
    }

    @GetMapping
    public ResponseEntity<ApiSucessResponse<List<ClienteResponseDto>>> listarClientes(){
        List<ClienteResponseDto> clientes = clienteService.listar();
        return ResponseEntity.ok(ApiSucessResponse.success(clientes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<ClienteResponseDto>> buscarClientePorId(
            @PathVariable Long id){
        ClienteResponseDto cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ApiSucessResponse.success(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<ClienteResponseDto>> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDto cliente){
        ClienteResponseDto clienteAtualizado = clienteService.atualizar(id, cliente);
        return ResponseEntity.ok(ApiSucessResponse.success("Cliente atualizado com sucesso", clienteAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSucessResponse<Void>> deletarCliente(
            @PathVariable Long id){
        clienteService.deletar(id);
        return ResponseEntity.ok(ApiSucessResponse.success("Cliente deletado com sucesso"));
    }


}
