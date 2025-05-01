package java.com.luizalabs.plataformacomunicacao;

import controllers.AgendamentoController;
import dto.AgendamentoRequest;
import enums.StatusComunicacao;
import enums.TipoComunicacao;
import interfaces.AgendamentoRepository;
import model.Agendamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlataformaComunicacaoApplicationTests {
	@Mock
	private AgendamentoRepository agendamentoRepository;

	@InjectMocks
	private AgendamentoController agendamentoController;

	private AgendamentoRequest request;

	@BeforeEach
	void setUp() {
		request = new AgendamentoRequest();
		request.setDataHoraEnvio(LocalDateTime.now().plusDays(1));
		request.setDestinatario("reviturino@gmail.com");
		request.setMensagem("Olá! Seu Agendamento foi concluido");
		request.setTipo(TipoComunicacao.EMAIL);
	}

	@Test
	void deveAgendarComunicacao_quandoRequestValido() {
		Agendamento agendamentoEsperado = Agendamento.builder()
				.id(1L)
				.dataHoraEnvio(request.getDataHoraEnvio())
				.destinatario(request.getDestinatario())
				.mensagem(request.getMensagem())
				.tipo(request.getTipo())
				.status(StatusComunicacao.AGENDADO)
				.build();
		when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamentoEsperado);
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(1L);
		assertThat(response.getBody().getDestinatario()).isEqualTo("reviturino@gmail.com");
		assertThat(response.getBody().getStatus()).isEqualTo(StatusComunicacao.AGENDADO);
		verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
	}

	@Test
	void naoDeveAgendarComDataNoPassado() {
		request.setDataHoraEnvio(LocalDateTime.now().minusDays(1));
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	void naoDeveAgendarComDestinatarioVazio() {
		request.setDestinatario("");
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	void deveRetornarErroQuandoRepositorioFalhar() {
		when(agendamentoRepository.save(any(Agendamento.class)))
				.thenThrow(new RuntimeException("Erro no banco"));
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertThat(response.getStatusCodeValue()).isEqualTo(500);
	}

	@Test
	void naoDeveAgendarComTipoInvalido() {
		request.setTipo(null);
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	void naoDeveAgendarComDataHoraEnvioNula() {
		request.setDataHoraEnvio(null);
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	void naoDeveAgendarComMensagemVazia() {
		request.setMensagem("");
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}

	@Test
	void deveConsultarAgendamentoExistente() {
		Agendamento agendamento = Agendamento.builder()
				.id(1L)
				.dataHoraEnvio(request.getDataHoraEnvio())
				.destinatario(request.getDestinatario())
				.mensagem(request.getMensagem())
				.tipo(request.getTipo())
				.status(StatusComunicacao.AGENDADO)
				.build();
		when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));
		ResponseEntity<Agendamento> response = agendamentoController.consultarStatus(1L);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(1L);
	}

	@Test
	void deveRetornarNotFoundAoConsultarAgendamentoInexistente() {
		when(agendamentoRepository.findById(999L)).thenReturn(Optional.empty());
		ResponseEntity<Agendamento> response = agendamentoController.consultarStatus(999L);
		assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	void deveRemoverAgendamentoExistente() {
		when(agendamentoRepository.existsById(1L)).thenReturn(true);
		doNothing().when(agendamentoRepository).deleteById(1L);
		ResponseEntity<Void> response = agendamentoController.removerAgendamento(1L);
		assertThat(response.getStatusCodeValue()).isEqualTo(204);
		verify(agendamentoRepository, times(1)).deleteById(1L);
	}

	@Test
	void deveRetornarNotFoundAoRemoverAgendamentoInexistente() {
		when(agendamentoRepository.existsById(999L)).thenReturn(false);
		ResponseEntity<Void> response = agendamentoController.removerAgendamento(999L);
		assertThat(response.getStatusCodeValue()).isEqualTo(404);
		verify(agendamentoRepository, never()).deleteById(anyLong());
	}
}

