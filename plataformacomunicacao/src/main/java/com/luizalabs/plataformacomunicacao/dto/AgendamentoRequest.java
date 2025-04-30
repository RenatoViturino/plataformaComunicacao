package dto;

import enums.TipoComunicacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "AgendamentoRequest", description = "Dados para agendar uma comunicação")

public class AgendamentoRequest {

    @NotNull(message = "Data/Hora do envio é obrigatória")
    @Schema(description = "Data e hora para o envio da comunicação", example = "2025-05-01T10:00:00")
    private LocalDateTime dataHoraEnvio;

    @NotBlank(message = "Destinatário é obrigatório")
    @Schema(description = "Destinatário da mensagem", example = "seu email@teste.com")
    private String destinatario;

    @NotBlank(message = "Mensagem é obrigatória")
    @Schema(description = "Conteúdo da mensagem", example = "Olá! Essa é uma mensagem de agendada.")
    private String mensagem;

    @NotNull(message = "Tipo de comunicação é obrigatório")
    @Schema(description = "Tipo da comunicação", example = "EMAIL")
    private TipoComunicacao tipo;
}
