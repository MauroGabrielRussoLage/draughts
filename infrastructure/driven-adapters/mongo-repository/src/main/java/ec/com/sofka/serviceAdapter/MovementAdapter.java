package ec.com.sofka.serviceAdapter;

import ec.com.sofka.entity.movement.Movement;
import ec.com.sofka.entity.player.Player;
import ec.com.sofka.gateway.dto.MovementDTO;
import ec.com.sofka.gateway.repository.MovementRepository;
import ec.com.sofka.gateway.repository.PlayerRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MovementAdapter implements MovementRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public MovementAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<MovementDTO> saveMovement(Mono<MovementDTO>  movement) {
        return null;
    }

    @Override
    public Flux<MovementDTO> findAll() {
        return null;
    }

    @Override
    public Mono<MovementDTO> updateMovement(Mono<MovementDTO>  movement) {
        return null;
    }
}
