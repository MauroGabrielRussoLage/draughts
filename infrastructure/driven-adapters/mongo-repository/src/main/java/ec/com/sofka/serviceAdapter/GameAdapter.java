package ec.com.sofka.serviceAdapter;

import ec.com.sofka.aggregate.value.GameId;
import ec.com.sofka.data.GameDocument;
import ec.com.sofka.gateway.dto.GameDTO;
import ec.com.sofka.gateway.repository.GameCommandSideRepository;
import ec.com.sofka.mapper.DocumentToDTOMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class GameAdapter implements GameCommandSideRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public GameAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Flux<GameDTO> findAll() {
        return reactiveMongoTemplate.findAll(GameDocument.class)
                .flatMap(gameDocument ->  DocumentToDTOMapper.toGame.apply(Mono.just(gameDocument)));
    }

    @Override
    public Mono<GameDTO> findById(Mono<GameId> gameIdMono) {
        return gameIdMono.flatMap(gameId ->
                reactiveMongoTemplate.findById(gameId.getValue(), GameDocument.class)
                        .flatMap(gameDocument ->  DocumentToDTOMapper.toGame.apply(Mono.just(gameDocument)))
        );
    }

    @Override
    public Mono<Boolean> existsGameWithPlayerAndNotFinalized(String playerName) {
        Query query = new Query();
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("status").ne("Game finalized"),
                new Criteria().orOperator(
                        Criteria.where("player1.name.value").is(playerName),
                        Criteria.where("player2.name.value").is(playerName)
                )
        );
        query.addCriteria(criteria);
        return reactiveMongoTemplate.exists(query, "Game");
    }
}

