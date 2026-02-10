package com.example.barbearia.service;

import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteModel criar(ClienteModel clienteModel) {
        return clienteRepository.save(clienteModel);
    }

    public List<ClienteModel> listar(){
        return clienteRepository.findAll();
    }

    public ClienteModel buscarPorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado cliente com esse ID"));
    }

    public ClienteModel atualizar(Long id, ClienteModel clienteModel) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado cliente com esse ID"));
        cliente.setId(id);
        return clienteRepository.save(clienteModel);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
