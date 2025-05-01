package java.com.luizalabs.plataformacomunicacao;

import controllers.AgendamentoController;
import enums.StatusComunicacao;
import enums.TipoComunicacao;
import interfaces.AgendamentoRepository;
import model.Agendamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerAdditionalTest {
    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Test
    void shouldReturnAgendamento_whenFound_inConsultarStatus() {

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .dataHoraEnvio(LocalDateTime.now().plusDays(1))
                .destinatario("reviturino@gmail.com")
                .mensagem("Agendamento existente")
                .tipo(TipoComunicacao.EMAIL)
                .status(StatusComunicacao.AGENDADO)
                .build();
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));
        ResponseEntity<Agendamento> response = agendamentoController.consultarStatus(1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnNotFound_whenAgendamentoNotFound_inConsultarStatus() {
        when(agendamentoRepository.findById(999L)).thenReturn(Optional.empty());
        ResponseEntity<Agendamento> response = agendamentoController.consultarStatus(999L);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void shouldRemoveAgendamento_whenExists() {

        when(agendamentoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(agendamentoRepository).deleteById(1L);
        ResponseEntity<Void> response = agendamentoController.removerAgendamento(1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(agendamentoRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnNotFound_whenRemovingNonExistingAgendamento() {

        when(agendamentoRepository.existsById(999L)).thenReturn(false);
        ResponseEntity<Void> response = agendamentoController.removerAgendamento(999L);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        verify(agendamentoRepository, never()).deleteById(anyLong());
    }
}
