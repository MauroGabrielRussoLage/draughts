package ec.com.sofka.gateway.mapper;

import ec.com.sofka.aggregate.Game;
import ec.com.sofka.gateway.querySideDTO.GameDTO;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class DTOToModelMapper {
    public static final Function<Mono<GameDTO>, Mono<Game>> toGame = account ->
            account.map(gameDTO -> {
                Game game = new Game();
                BeanUtils.copyProperties(gameDTO, game);
                return game;
            });
}
