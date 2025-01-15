package ec.com.sofka.useCase;

import ec.com.sofka.aggregate.Game;
import ec.com.sofka.entity.player.Player;
import ec.com.sofka.entity.player.value.object.Name;
import ec.com.sofka.entity.player.value.object.PieceColor;
import ec.com.sofka.gateway.querySideDTO.GameDTO;
import ec.com.sofka.gateway.repository.GameQuerySideRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateViewGameUseCase {

    private final GameQuerySideRepository repository;

    public CreateViewGameUseCase(GameQuerySideRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> apply(Mono<GameDTO> gameDTOMono) {
        return repository.saveGame(gameDTOMono);
    }
}
