package java.com.luizalabs.plataformacomunicacao;

import controllers.AgendamentoController;
import dto.AgendamentoRequest;
import enums.StatusComunicacao;
import enums.TipoComunicacao;
import interfaces.AgendamentoRepository;
import model.Agendamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {
    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoController agendamentoController;

    private AgendamentoRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new AgendamentoRequest();
        validRequest.setDataHoraEnvio(LocalDateTime.now().plusDays(1));
        validRequest.setDestinatario("reviturino@gmail.com");
        validRequest.setMensagem("Olá! Seu Agendamento foi concluido");
        validRequest.setTipo(TipoComunicacao.EMAIL);
    }

    @Test
    void shouldScheduleCommunication_whenValidRequestProvided() {

        Agendamento agendamentoExpected = Agendamento.builder()
                .id(1L)
                .dataHoraEnvio(validRequest.getDataHoraEnvio())
                .destinatario(validRequest.getDestinatario())
                .mensagem(validRequest.getMensagem())
                .tipo(validRequest.getTipo())
                .status(StatusComunicacao.AGENDADO)
                .build();
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamentoExpected);
        ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(validRequest);


        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getDestinatario()).isEqualTo("reviturino@gmail.com");
        assertThat(response.getBody().getStatus()).isEqualTo(StatusComunicacao.AGENDADO);
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
    }

    @Test
    void shouldReturnBadRequest_whenSchedulingWithPastDate() {
        validRequest.setDataHoraEnvio(LocalDateTime.now().minusDays(1));

        ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(validRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void shouldReturnBadRequest_whenDestinatarioIsEmpty() {
        validRequest.setDestinatario("");
        ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(validRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void shouldReturnServerError_whenRepositoryThrowsException() {
        when(agendamentoRepository.save(any(Agendamento.class)))
                .thenThrow(new RuntimeException("Erro no banco"));

        ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(validRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    void shouldReturnBadRequest_whenTipoIsNull() {
        validRequest.setTipo(null);
        ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(validRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void shouldReturnBadRequest_whenDataHoraEnvioIsNull() {
        validRequest.setDataHoraEnvio(null);
        ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(validRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void shouldReturnBadRequest_whenMensagemIsEmpty() {
        validRequest.setMensagem("");
        ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(validRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }
}