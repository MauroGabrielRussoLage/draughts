package ec.com.sofka.mapper;

import ec.com.sofka.data.BoardDocument;
import ec.com.sofka.data.MovementDocument;
import ec.com.sofka.data.PlayerDocument;
import ec.com.sofka.gateway.dto.BoardDTO;
import ec.com.sofka.gateway.dto.MovementDTO;
import ec.com.sofka.gateway.dto.PlayerDTO;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class DTOToDocumentMapper {
    public static final Function<Mono<BoardDTO>, Mono<BoardDocument>> toBoard = account ->
            account.map(boardDTO -> {
                BoardDocument boardDocument = new BoardDocument();
                BeanUtils.copyProperties(boardDTO, boardDocument);
                return boardDocument;
            });
    public static final Function<Mono<MovementDTO>, Mono<MovementDocument>> toMovement = account ->
            account.map(movementDTO -> {
                MovementDocument movementDocument = new MovementDocument();
                BeanUtils.copyProperties(movementDTO, movementDocument);
                return movementDocument;
            });
    public static final Function<Mono<PlayerDTO>, Mono<PlayerDocument>> toPlayer = account ->
            account.map(playerDTO -> {
                PlayerDocument playerDocument = new PlayerDocument();
                BeanUtils.copyProperties(playerDTO, playerDocument);
                return playerDocument;
            });
}
