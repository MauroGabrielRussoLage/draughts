package ec.com.sofka.adapter;

import ec.com.sofka.gateway.dto.GameDTO;
import ec.com.sofka.gateway.repository.GameQuerySideRepository;
import ec.com.sofka.mapper.DTOToDocumentMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CreateAdapter implements GameQuerySideRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public CreateAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Void> saveGame(Mono<GameDTO> gameMono) {
        return gameMono.flatMap(gameDTO -> DTOToDocumentMapper.toGame.apply(Mono.just(gameDTO))
                .flatMap(gameDocument -> reactiveMongoTemplate.save(gameDocument).then()));
    }

    @Override
    public Mono<GameDTO> updateGame(Mono<GameDTO> game) {
        return null;
    }
}