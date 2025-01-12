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

public class DocumentToDTOMapper {
    public static final Function<Mono<BoardDocument>, Mono<BoardDTO>> toBoard = account ->
            account.map(boardDocument -> {
                BoardDTO boardDTO = new BoardDTO();
                BeanUtils.copyProperties(boardDocument, boardDTO);
                return boardDTO;
            });
    public static final Function<Mono<MovementDocument>, Mono<MovementDTO>> toMovement = account ->
            account.map(movementDocument -> {
                MovementDTO movementDTO = new MovementDTO();
                BeanUtils.copyProperties(movementDocument, movementDTO);
                return movementDTO;
            });
    public static final Function<Mono<PlayerDocument>, Mono<PlayerDTO>> toPlayer = account ->
            account.map(playerDocument -> {
                PlayerDTO playerDTO = new PlayerDTO();
                BeanUtils.copyProperties(playerDocument, playerDTO);
                return playerDTO;
            });
}
