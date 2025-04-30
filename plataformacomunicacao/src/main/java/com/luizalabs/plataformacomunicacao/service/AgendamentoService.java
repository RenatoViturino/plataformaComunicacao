package service;

import enums.StatusComunicacao;
import model.Agendamento;
import dto.AgendamentoRequest;
import interfaces.AgendamentoRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AgendamentoService {
    private final AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository) {
        this.repository = repository;
    }

    public Agendamento agendar(AgendamentoRequest request) {
        Agendamento agendamento = Agendamento.builder()
                .dataHoraEnvio(request.getDataHoraEnvio())
                .destinatario(request.getDestinatario())
                .mensagem(request.getMensagem())
                .tipo(request.getTipo())
                .status(StatusComunicacao.AGENDADO)
                .build();
        return repository.save(agendamento);
    }

    public Optional<Agendamento> consultar(Long id) {
        return repository.findById(id);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }
}
