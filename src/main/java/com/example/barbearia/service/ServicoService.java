package com.example.barbearia.service;

import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import com.example.barbearia.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public ServicoModel criar(ServicoModel servicoModel) {
        return servicoRepository.save(servicoModel);
    }

    public List<ServicoModel> listar(){
        return servicoRepository.findAll();
    }

    public ServicoModel buscarPorId(Long id){
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado serviço com esse ID"));
    }

    public ServicoModel atualizar(Long id, ServicoModel servicoModel) {
        ServicoModel servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado serviço com esse ID"));
        servico.setId(id);
        return servicoRepository.save(servicoModel);
    }

    public void deletar(Long id) {
        servicoRepository.deleteById(id);
    }
}
