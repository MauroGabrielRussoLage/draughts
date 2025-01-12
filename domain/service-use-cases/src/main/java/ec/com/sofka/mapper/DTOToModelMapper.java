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

public class DTOToModelMapper {
    public static final Function<Mono<BoardDTO>, Mono<Board>> toBoard = account ->
            account.map(boardDTO -> {
                Board board = new Board();
                BeanUtils.copyProperties(boardDTO, board);
                return board;
            });
    public static final Function<Mono<MovementDTO>, Mono<Movement>> toMovement = account ->
            account.map(movementDTO -> {
                Movement movement = new Movement();
                BeanUtils.copyProperties(movementDTO, movement);
                return movement;
            });
    public static final Function<Mono<PlayerDTO>, Mono<Player>> toPlayer = account ->
            account.map(playerDTO -> {
                Player player = new Player();
                BeanUtils.copyProperties(playerDTO, player);
                return player;
            });
}
