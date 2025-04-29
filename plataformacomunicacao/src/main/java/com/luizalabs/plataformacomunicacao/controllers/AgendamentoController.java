package com.luizalabs.plataformacomunicacao.controllers;

import com.luizalabs.plataformacomunicacao.model.Agendamento;
import com.luizalabs.plataformacomunicacao.model.AgendamentoRequest;
import com.luizalabs.plataformacomunicacao.service.AgendamentoRepository;
import com.luizalabs.plataformacomunicacao.tipo.StatusComunicacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoRepository repository;

    @PostMapping("/agendar")
    public ResponseEntity<Agendamento> agendarComunicacao(@RequestBody AgendamentoRequest request) {
        Agendamento agendamento = Agendamento.builder()
                .dataHoraEnvio(request.getDataHoraEnvio())
                .destinatario(request.getDestinatario())
                .mensagem(request.getMensagem())
                .tipo(request.getTipo())
                .status(StatusComunicacao.AGENDADO)
                .build();

        Agendamento salvo = repository.save(agendamento);
        return ResponseEntity.ok(salvo);
    }
}
