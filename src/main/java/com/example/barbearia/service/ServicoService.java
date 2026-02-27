package com.example.barbearia.service;

import com.example.barbearia.dto.request.ServicoRequestDto;
import com.example.barbearia.dto.response.ServicoResponseDto;
import com.example.barbearia.mapper.ServicoMapper;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import com.example.barbearia.repository.AgendamentoRepository;
import com.example.barbearia.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper;
    private final AgendamentoRepository agendamentoRepository;

    public ServicoService(ServicoRepository servicoRepository, ServicoMapper servicoMapper, AgendamentoRepository agendamentoRepository) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
        this.agendamentoRepository = agendamentoRepository;
    }

    @Transactional
    public ServicoResponseDto criar(ServicoRequestDto dto) {
        ServicoModel model = servicoMapper.toModel(dto);
        ServicoModel save = servicoRepository.save(model);
        return servicoMapper.toResponseDto(save);
    }

    @Transactional(readOnly = true)
    public List<ServicoResponseDto> listar(){
        List<ServicoModel> all = servicoRepository.findAll();
        return servicoMapper.toResponseDTOList(all);
    }

    @Transactional(readOnly = true)
    public ServicoResponseDto buscarPorId(Long id){
        ServicoModel model = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado serviço com esse ID"));
        return servicoMapper.toResponseDto(model);
    }

    @Transactional
    public ServicoResponseDto atualizar(Long id, ServicoRequestDto dto) {
        ServicoModel servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado serviço com esse ID"));
        servicoMapper.updateModel(dto, servico);
        ServicoModel update = servicoRepository.save(servico);
        return servicoMapper.toResponseDto(update);
    }

    @Transactional
    public void deletar(Long id) {
        if(!servicoRepository.existsById(id)){
           throw new RuntimeException("ID não encontrado");
        }
        if(agendamentoRepository.existsByServicoId(id)){
            throw new RuntimeException("Serviço possui agendamentos vinculados e não pode ser deletado");
        }
        servicoRepository.deleteById(id);
    }
}
