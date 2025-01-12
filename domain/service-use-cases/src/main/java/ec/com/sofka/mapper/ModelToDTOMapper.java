package ec.com.sofka.mapper;

import ec.com.sofka.entity.board.Board;
import ec.com.sofka.entity.movement.Movement;
import ec.com.sofka.entity.player.Player;
import ec.com.sofka.gateway.dto.BoardDTO;
import ec.com.sofka.gateway.dto.MovementDTO;
import ec.com.sofka.gateway.dto.PlayerDTO;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ModelToDTOMapper {
    public static final Function<Mono<Board>, Mono<BoardDTO>> toBoard = account ->
            account.map(board -> {
                BoardDTO boardDTO = new BoardDTO();
                BeanUtils.copyProperties(board, boardDTO);
                return boardDTO;
            });
    public static final Function<Mono<Movement>, Mono<MovementDTO>> toMovement = account ->
            account.map(movement -> {
                MovementDTO movementDTO = new MovementDTO();
                BeanUtils.copyProperties(movement, movementDTO);
                return movementDTO;
            });
    public static final Function<Mono<Player>, Mono<PlayerDTO>> toPlayer = account ->
            account.map(player -> {
                PlayerDTO playerDTO = new PlayerDTO();
                BeanUtils.copyProperties(player, playerDTO);
                return playerDTO;
            });
}
