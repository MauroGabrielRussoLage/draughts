package ec.com.sofka.gateway.repository;

import ec.com.sofka.aggregate.value.GameId;
import ec.com.sofka.gateway.dto.GameDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameCommandSideRepository {
    Flux<GameDTO> findAll();

    Mono<GameDTO> findById(Mono<GameId> game);

    Mono<Boolean> existsGameWithPlayerAndNotFinalized(String playerName);
}