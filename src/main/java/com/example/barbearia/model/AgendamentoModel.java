package com.example.barbearia.model;

import com.example.barbearia.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoModel {

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
    private Status status;

    @PrePersist
    @PreUpdate
    void onCreateEnd(){
        if(horaInicio != null && servico != null && servico.getDuracao() != null){
            this.horaFim = horaInicio.plusMinutes(servico.getDuracao());
        }
    }

    public void calcularHoraFim(){
        if(horaInicio == null || servico == null || servico.getDuracao() == null){
            throw new IllegalStateException("Não é possível calcular hora fim");
        }
        this.horaFim = horaInicio.plusMinutes(servico.getDuracao());
    }

    public static boolean conflita(LocalTime inicioNovo, LocalTime fimNovo,
                                   LocalTime inicioExistente, LocalTime fimExistente){
        return inicioNovo.isBefore(fimExistente) &&
                fimNovo.isAfter(inicioExistente);
    }

    public boolean dataValida(){
        return data != null && !this.data.isBefore(LocalDate.now());
    }

    public void validar(){
        if (data == null) {
            throw new IllegalArgumentException("Data é obrigatória");
        }

        if (horaInicio == null) {
            throw new IllegalArgumentException("Hora início é obrigatória");
        }

        if (servico == null) {
            throw new IllegalArgumentException("Serviço é obrigatório");
        }

        if(servico.getDuracao() == null || servico.getDuracao() <= 0){
            throw new IllegalArgumentException("Duração do serviço inválida");
        }

        calcularHoraFim();

        if(!horaFim.isAfter(horaInicio)){
            throw new IllegalArgumentException("Hora fim deve ser maior que hora início");
        }

    }



}
