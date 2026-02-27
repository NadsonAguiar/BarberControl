package com.example.barbearia.service;

import com.example.barbearia.dto.request.ClienteRequestDto;
import com.example.barbearia.dto.response.ClienteResponseDto;
import com.example.barbearia.mapper.ClienteMapper;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.repository.AgendamentoRepository;
import com.example.barbearia.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final AgendamentoRepository agendamentoRepository;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, AgendamentoRepository agendamentoRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.agendamentoRepository = agendamentoRepository;
    }


    @Transactional
    public ClienteResponseDto criar(ClienteRequestDto request) {
        ClienteModel cliente = clienteMapper.toModel(request);
        return clienteMapper.toResponseDto(clienteRepository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDto> listar(){
        List<ClienteModel> clientes = clienteRepository.findAll();
        return clienteMapper.toResponseDTOList(clientes);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDto buscarPorId(Long id){
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado cliente com esse ID"));
        return clienteMapper.toResponseDto(cliente);
    }

    @Transactional
    public ClienteResponseDto atualizar(Long id, ClienteRequestDto request) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado cliente com esse ID"));
        clienteMapper.toUpdateModel(request, cliente);
        ClienteModel update = clienteRepository.save(cliente);
        return clienteMapper.toResponseDto(update);
    }

    @Transactional
    public void deletar(Long id) {
        if(!clienteRepository.existsById(id)) {
            throw new RuntimeException("ID não encontrado");
        }
        if(agendamentoRepository.existsByClienteId(id)){
            throw new RuntimeException("Cliente possui agendamentos vinculados e não pode ser deletado");
        }
        clienteRepository.deleteById(id);
    }
}
