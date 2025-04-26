package com.luizalabs.plataformacomunicacao.model;

import com.luizalabs.plataformacomunicacao.tipo.TipoComunicacao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoRequest {
    private LocalDateTime dataHoraEnvio;
    private String destinatario;
    private String mensagem;
    private TipoComunicacao tipo;
}
