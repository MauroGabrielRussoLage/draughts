package ec.com.sofka.gateway.repository;

import ec.com.sofka.aggregate.Game;
import ec.com.sofka.command.GameCommand;
import ec.com.sofka.gateway.dto.GameDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameRepository {
    Mono<GameDTO> saveGame(Mono<GameDTO> game);

    Flux<GameDTO> findAll();

    Mono<GameDTO> updateGame(Mono<GameDTO> game);
}