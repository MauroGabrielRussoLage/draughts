package ec.com.sofka.useCase;

import ec.com.sofka.gateway.dto.GameDTO;
import ec.com.sofka.gateway.repository.GameQuerySideRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UpdateViewGameUseCase {

    private final GameQuerySideRepository repository;

    public UpdateViewGameUseCase(GameQuerySideRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> apply(Mono<GameDTO> log) {
        return repository.saveGame(log);
    }
}
