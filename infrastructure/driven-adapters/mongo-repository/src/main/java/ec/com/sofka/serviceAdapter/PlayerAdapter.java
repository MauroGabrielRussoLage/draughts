package ec.com.sofka.serviceAdapter;

import ec.com.sofka.data.PlayerDocument;
import ec.com.sofka.gateway.dto.PlayerDTO;
import ec.com.sofka.gateway.repository.PlayerRepository;
import ec.com.sofka.mapper.DocumentToDTOMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PlayerAdapter implements PlayerRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public PlayerAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<PlayerDTO> saveMovement(Mono<PlayerDTO> player) {
        return null;
    }

    @Override
    public Flux<PlayerDTO> findAll() {
        return reactiveMongoTemplate.findAll(PlayerDocument.class)
                .flatMap(playerDocument -> DocumentToDTOMapper.toPlayer.apply(Mono.just(playerDocument)));
    }

    @Override
    public Mono<PlayerDTO> updateMovement(Mono<PlayerDTO> player) {
        return null;
    }
}
