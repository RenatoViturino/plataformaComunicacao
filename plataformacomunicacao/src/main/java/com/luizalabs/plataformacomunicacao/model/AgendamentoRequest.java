package com.luizalabs.plataformacomunicacao.model;

import com.luizalabs.plataformacomunicacao.tipo.TipoComunicacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "AgendamentoRequest", description = "Dados para agendar uma comunicação")

public class AgendamentoRequest {

    @Schema(description = "Data e hora para o envio da comunicação", example = "2025-05-01T10:00:00")
    private LocalDateTime dataHoraEnvio;

    @Schema(description = "Destinatário da mensagem", example = "seu email@teste.com")
    private String destinatario;

    @Schema(description = "Conteúdo da mensagem", example = "Olá! Essa é uma mensagem de agendada.")
    private String mensagem;

    @Schema(description = "Tipo da comunicação", example = "EMAIL")
    private TipoComunicacao tipo;
}
