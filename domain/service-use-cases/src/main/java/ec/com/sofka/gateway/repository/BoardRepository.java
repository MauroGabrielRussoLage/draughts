package ec.com.sofka.gateway.repository;

import ec.com.sofka.entity.board.Board;
import ec.com.sofka.gateway.dto.BoardDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BoardRepository {
    Mono<BoardDTO> saveBoard(Mono<BoardDTO> board);

    Flux<BoardDTO> findAll();

    Mono<BoardDTO> updateBoard(Mono<BoardDTO> board);
}