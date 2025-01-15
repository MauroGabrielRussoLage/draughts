package ec.com.sofka.gateway.repository;


import ec.com.sofka.aggregate.Game;
import ec.com.sofka.gateway.querySideDTO.GameDTO;
import reactor.core.publisher.Mono;

public interface GameQuerySideRepository {

    Mono<Void> saveGame(Mono<GameDTO> game);

    Mono<Void> updateGame(Mono<GameDTO> game);
}