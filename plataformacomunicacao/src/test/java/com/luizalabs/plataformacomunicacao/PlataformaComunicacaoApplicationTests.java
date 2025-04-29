package com.luizalabs.plataformacomunicacao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
	void deveAgendarComunicacao() {
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
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(1L, response.getBody().getId());
		assertEquals("reviturino@gmail.com", response.getBody().getDestinatario());
		assertEquals(StatusComunicacao.AGENDADO, response.getBody().getStatus());

		verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
	}
	@Test
	void naoDeveAgendarComDataNoPassado() {

		request.setDataHoraEnvio(LocalDateTime.now().minusDays(1));
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertEquals(400, response.getStatusCodeValue());
	}
	@Test
	void naoDeveAgendarComDestinatarioVazio() {

		request.setDestinatario("");
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertEquals(400, response.getStatusCodeValue());
	}

	@Test
	void deveRetornarErroQuandoRepositorioFalhar() {

		when(agendamentoRepository.save(any(Agendamento.class))).thenThrow(new RuntimeException("Erro no banco"));
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertEquals(500, response.getStatusCodeValue());
	}
	@Test
	void naoDeveAgendarComTipoInvalido() {

		request.setTipo(null);
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertEquals(400, response.getStatusCodeValue());
	}
	@Test
	void naoDeveAgendarComDataHoraEnvioNula() {

		request.setDataHoraEnvio(null);
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertEquals(400, response.getStatusCodeValue());
	}

	@Test
	void naoDeveAgendarComMensagemVazia() {
		request.setMensagem("");
		ResponseEntity<Agendamento> response = agendamentoController.agendarComunicacao(request);
		assertEquals(400, response.getStatusCodeValue());
	}


}

