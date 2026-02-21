package com.example.barbearia.service;

import com.example.barbearia.dto.request.ServicoRequestDto;
import com.example.barbearia.dto.response.ServicoResponseDto;
import com.example.barbearia.mapper.ServicoMapper;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import com.example.barbearia.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper;

    public ServicoService(ServicoRepository servicoRepository, ServicoMapper servicoMapper) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
    }

    public ServicoResponseDto criar(ServicoRequestDto dto) {
        ServicoModel model = servicoMapper.toModel(dto);
        ServicoModel save = servicoRepository.save(model);
        return servicoMapper.toResponseDto(save);
    }

    public List<ServicoResponseDto> listar(){
        List<ServicoModel> all = servicoRepository.findAll();
        return servicoMapper.toResponseDTOList(all);
    }

    public ServicoResponseDto buscarPorId(Long id){
        ServicoModel model = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado serviço com esse ID"));
        return servicoMapper.toResponseDto(model);
    }

    public ServicoResponseDto atualizar(Long id, ServicoRequestDto dto) {
        ServicoModel servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado serviço com esse ID"));
        servicoMapper.updateModel(dto, servico);
        ServicoModel update = servicoRepository.save(servico);
        return servicoMapper.toResponseDto(update);
    }

    public void deletar(Long id) {
        if(!servicoRepository.existsById(id)){
           throw new RuntimeException("ID não encontrado");
        }
        servicoRepository.deleteById(id);
    }
}
