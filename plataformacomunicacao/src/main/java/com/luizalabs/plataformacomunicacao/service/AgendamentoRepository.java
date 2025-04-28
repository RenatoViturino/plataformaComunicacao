package com.luizalabs.plataformacomunicacao.service;

import com.luizalabs.plataformacomunicacao.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {}
