package com.example.barbearia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
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

        @Column(name = "duracao")
        private Integer duracao;

        @Column(name = "preco", nullable = false)
        private Double preco;

        @OneToMany(mappedBy = "servico")
        private List<AgendamentoModel> agendamentos;
}
