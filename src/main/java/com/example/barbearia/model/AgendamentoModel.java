package com.example.barbearia.model;

import com.example.barbearia.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoModel {

    private static final Log log = LogFactory.getLog(AgendamentoModel.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private ServicoModel servico;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim")
    private LocalTime horaFim;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.MARCADO;

    @PrePersist
    @PreUpdate
    void onCreateEnd(){
        if( servico == null ){
            log.warn("Serviço está null antes do update na entidade id: " + id);
            throw new IllegalStateException("Serviço não pode ser null ao atualizar");
        }
        if(horaInicio != null && servico.getDuracao() != null){
            this.horaFim = horaInicio.plusMinutes(servico.getDuracao());
        }
    }
}
