package java.com.luizalabs.plataformacomunicacao;

import enums.StatusComunicacao;
import enums.TipoComunicacao;
import interfaces.AgendamentoRepository;
import model.Agendamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.AgendamentoService;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {
    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Test
    void shouldReturnAgendamento_whenExists() {

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .dataHoraEnvio(LocalDateTime.now().plusDays(1))
                .destinatario("reviturino@gmail.com")
                .mensagem("Agendamento existente")
                .tipo(TipoComunicacao.EMAIL)
                .status(StatusComunicacao.AGENDADO)
                .build();
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));
        Optional<Agendamento> result = agendamentoService.consultar(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnEmpty_whenAgendamentoNotFound() {
        when(agendamentoRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<Agendamento> result = agendamentoService.consultar(999L);
        assertThat(result).isNotPresent();
    }
}
