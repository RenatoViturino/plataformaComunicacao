package model;


import enums.StatusComunicacao;
import enums.TipoComunicacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraEnvio;

    private String destinatario;

    private String mensagem;

    @Enumerated(EnumType.STRING)
    private TipoComunicacao tipo;

    @Enumerated(EnumType.STRING)
    private StatusComunicacao status;
}
