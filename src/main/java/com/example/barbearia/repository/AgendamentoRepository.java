package com.example.barbearia.repository;

import com.example.barbearia.enums.Status;
import com.example.barbearia.model.AgendamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long> {

    List<AgendamentoModel> findByDataAndStatusInOrderByHoraInicioAsc(LocalDate dataInicial, List<Status> status);

}
