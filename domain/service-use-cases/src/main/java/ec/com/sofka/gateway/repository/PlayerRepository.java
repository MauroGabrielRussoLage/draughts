package ec.com.sofka.gateway.repository;

import ec.com.sofka.entity.player.Player;
import ec.com.sofka.gateway.dto.PlayerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerRepository {
    Mono<PlayerDTO> saveMovement(Mono<PlayerDTO> player);

    Flux<PlayerDTO> findAll();

    Mono<PlayerDTO> updateMovement(Mono<PlayerDTO> player);
}