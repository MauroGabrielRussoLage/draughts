package ec.com.sofka.serviceAdapter;

import ec.com.sofka.entity.board.Board;
import ec.com.sofka.entity.player.Player;
import ec.com.sofka.gateway.dto.BoardDTO;
import ec.com.sofka.gateway.repository.BoardRepository;
import ec.com.sofka.gateway.repository.PlayerRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BoardAdapter implements BoardRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public BoardAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<BoardDTO> saveBoard(Mono<BoardDTO> board) {
        return null;
    }

    @Override
    public Flux<BoardDTO> findAll() {
        return null;
    }

    @Override
    public Mono<BoardDTO> updateBoard(Mono<BoardDTO> board) {
        return null;
    }
}
