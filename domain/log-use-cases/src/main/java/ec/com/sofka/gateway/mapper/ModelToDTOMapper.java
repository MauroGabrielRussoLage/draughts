package ec.com.sofka.gateway.mapper;

import ec.com.sofka.aggregate.Game;
import ec.com.sofka.gateway.querySideDTO.GameDTO;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ModelToDTOMapper {
    public static final Function<Mono<Game>, Mono<GameDTO>> toBoard = account ->
            account.map(game -> {
                GameDTO gameDTO = new GameDTO();
                BeanUtils.copyProperties(game, gameDTO);
                return gameDTO;
            });
}
