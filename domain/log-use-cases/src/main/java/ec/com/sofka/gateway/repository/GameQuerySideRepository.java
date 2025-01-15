package ec.com.sofka.gateway.repository;


import ec.com.sofka.gateway.dto.GameDTO;
import reactor.core.publisher.Mono;

public interface GameQuerySideRepository {

    Mono<Void> saveGame(Mono<GameDTO> game);

    Mono<GameDTO> updateGame(Mono<GameDTO> game);
}