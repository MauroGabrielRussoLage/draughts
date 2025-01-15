package ec.com.sofka.adapter;

import ec.com.sofka.data.GameDocument;
import ec.com.sofka.gateway.querySideDTO.GameDTO;
import ec.com.sofka.gateway.repository.GameQuerySideRepository;
import ec.com.sofka.mapper.DTOToDocumentMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    public Mono<Void> updateGame(Mono<GameDTO> gameMono) {
        return gameMono.flatMap(gameDTO -> DTOToDocumentMapper.toGame.apply(Mono.just(gameDTO))
                .flatMap(gameDocument -> {
                    Query query = new Query(Criteria.where("_id").is(gameDocument.getId()));
                    Update update = buildUpdateFromGameDocument(gameDocument);
                    return reactiveMongoTemplate.updateFirst(query, update, GameDocument.class).then();
                }));
    }

    private Update buildUpdateFromGameDocument(GameDocument gameDocument) {
        Update update = new Update();
        if (gameDocument.getPlayer1() != null) {
            update.set("player1", gameDocument.getPlayer1());
        }
        if (gameDocument.getPlayer2() != null) {
            update.set("player2", gameDocument.getPlayer2());
        }
        if (gameDocument.getBoard() != null) {
            update.set("board", gameDocument.getBoard());
        }
        if (gameDocument.getStatus() != null) {
            update.set("status", gameDocument.getStatus());
        }
        if (gameDocument.getCurrentTurn() != null) {
            update.set("currentTurn", gameDocument.getCurrentTurn());
        }
        if (gameDocument.getWinner() != null) {
            update.set("winner", gameDocument.getWinner());
        }
        if (gameDocument.getStartDate() != null) {
            update.set("startDate", gameDocument.getStartDate());
        }
        if (gameDocument.getEndDate() != null) {
            update.set("endDate", gameDocument.getEndDate());
        }
        if (gameDocument.getMovements() != null) {
            update.set("movements", gameDocument.getMovements());
        }
        return update;
    }
}