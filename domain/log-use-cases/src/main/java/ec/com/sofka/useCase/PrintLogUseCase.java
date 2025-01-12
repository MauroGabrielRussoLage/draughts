package ec.com.sofka.useCase;

import ec.com.sofka.entity.log.GameEventLog;
import ec.com.sofka.gateway.repository.LogRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PrintLogUseCase {

    private final LogRepository repository;

    public PrintLogUseCase(LogRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> apply(Mono<GameEventLog> log) {
        return repository.createLog(log);
    }
}
