package com.example.barbearia.mapper;

import com.example.barbearia.dto.request.ClienteRequestDto;
import com.example.barbearia.dto.request.ServicoRequestDto;
import com.example.barbearia.dto.response.ServicoResponseDto;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicoMapper {

    // Converte DTO de requisição para Model
    public ServicoModel toModel(ServicoRequestDto dto) {
        ServicoModel model = new ServicoModel();
        model.setNome(dto.nome());
        model.setDuracao(dto.duracao());
        model.setPreco(dto.preco());
        return model;
    }

    // Converte Model para DTO de resposta
    public ServicoResponseDto toResponseDto(ServicoModel model){
        return new ServicoResponseDto(
                model.getId(),
                model.getNome(),
                model.getDuracao(),
                model.getPreco()
        );
    }

    // Converte lista de Models para lista de DTOs
    public List<ServicoResponseDto> toResponseDTOList(List<ServicoModel> models){
        return models
                .stream()
                .map(this::toResponseDto)
                .toList();
    }


}
