package com.example.barbearia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "servico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicoModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nome", nullable = false)
        private String nome;

        @Column(name = "duracao", nullable = false)
        private Integer duracao;

        @Column(name = "preco", nullable = false)
        private BigDecimal preco;

        @OneToMany(mappedBy = "servico")
        @JsonIgnore
        private List<AgendamentoModel> agendamentos;
}
