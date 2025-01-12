package ec.com.sofka.gateway.repository;

import ec.com.sofka.gateway.dto.MovementDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementRepository {
    Mono<MovementDTO> saveMovement(Mono<MovementDTO> movement);

    Flux<MovementDTO> findAll();

    Mono<MovementDTO> updateMovement(Mono<MovementDTO> movement);
}