package com.luizalabs.plataformacomunicacao.controllers;

import com.luizalabs.plataformacomunicacao.model.Agendamento;
import com.luizalabs.plataformacomunicacao.model.AgendamentoRequest;
import com.luizalabs.plataformacomunicacao.service.AgendamentoRepository;
import com.luizalabs.plataformacomunicacao.tipo.StatusComunicacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Agendamentos", description = "Endpoints para agendar, consultar e remover comunicações")
@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoRepository repository;
    @Operation(summary = "Agendar envio de comunicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
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
    @Operation(summary = "Consultar status de agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @GetMapping("/consultar/{id}")
    public ResponseEntity<Agendamento> consultarStatus(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remover agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> removerAgendamento(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
