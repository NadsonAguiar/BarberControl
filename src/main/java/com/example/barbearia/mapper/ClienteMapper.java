package com.example.barbearia.mapper;

import com.example.barbearia.dto.request.ClienteRequestDto;
import com.example.barbearia.dto.response.ClienteResponseDto;
import com.example.barbearia.model.ClienteModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {

    // Converte DTO de requisição para Model
    public ClienteModel toModel(ClienteRequestDto dto) {
            ClienteModel model = new ClienteModel();
            model.setNome(dto.nome());
            model.setTelefone(dto.telefone());
            return model;
    }


    //Atualiza um Model existente com dados do DTO (usado para edição)
    public void toUpdateModel(ClienteRequestDto dto, ClienteModel model){
        model.setNome(dto.nome());
        model.setTelefone(dto.telefone());
    }

    // Converte Model para DTO de resposta
    public ClienteResponseDto toResponseDto(ClienteModel model){
        return new ClienteResponseDto(
                model.getId(),
                model.getNome(),
                model.getTelefone()
        );
    }

    // Converte lista de Models para lista de DTOs
    public List<ClienteResponseDto> toResponseDTOList(List<ClienteModel> models){
        return models
                .stream()
                .map(this::toResponseDto)
                .toList();
    }


}
