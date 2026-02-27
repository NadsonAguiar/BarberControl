package com.example.barbearia.mapper;

import com.example.barbearia.dto.request.AgendamentoPatchDto;
import com.example.barbearia.dto.request.AgendamentoRequestDto;
import com.example.barbearia.dto.request.ClienteRequestDto;
import com.example.barbearia.dto.request.ServicoRequestDto;
import com.example.barbearia.dto.response.AgendamentoResponseDto;
import com.example.barbearia.dto.response.ClienteResponseDto;
import com.example.barbearia.model.AgendamentoModel;
import com.example.barbearia.model.ClienteModel;
import com.example.barbearia.model.ServicoModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgendamentoMapper {

    // Converte DTO de requisição para Model
    public AgendamentoModel toModel(ClienteModel cliente, ServicoModel servico, AgendamentoRequestDto dto){
        AgendamentoModel model = new AgendamentoModel();
        model.setCliente(cliente);
        model.setServico(servico);
        model.setData(dto.getData());
        model.setHoraInicio(dto.getHoraInicio());
        return model;
    }

    // Converte Model para DTO de resposta
    public AgendamentoResponseDto toResponseDto(AgendamentoModel model){
        AgendamentoResponseDto dto = new AgendamentoResponseDto();
        dto.setId(model.getId());
        dto.setClienteId(model.getCliente().getId());
        dto.setServicoId(model.getServico().getId());
        dto.setData(model.getData());
        dto.setHoraInicio(model.getHoraInicio());
        dto.setHoraFim(model.getHoraFim());
        dto.setStatus(model.getStatus());
        return dto;
    }

    //Atualiza um Model existente com dados do DTO (usado para edição)
    public void updateModel(AgendamentoRequestDto dto, ClienteModel cliente,ServicoModel servico, AgendamentoModel model){
        model.setCliente(cliente);
        model.setServico(servico);
        model.setData(dto.getData());
        model.setHoraInicio(dto.getHoraInicio());
        model.setStatus(dto.getStatus());
    }

    public void updatePatchModel(AgendamentoPatchDto dto, ClienteModel cliente, ServicoModel servico, AgendamentoModel model){
        if( dto.getClienteId() != null){
            model.setCliente(cliente);
        }
        if (dto.getServicoId() != null){
            model.setServico(servico);
        }
        if (dto.getData() != null){
            model.setData(dto.getData());
        }
        if (dto.getHoraInicio() != null){
            model.setHoraInicio(dto.getHoraInicio());
        }
        if (dto.getStatus() != null){
            model.setStatus(dto.getStatus());
        }
    }


    // Converte lista de Models para lista de DTOs
    public List<AgendamentoResponseDto> toResponseDTOList(List<AgendamentoModel> models){
        return models
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

}
