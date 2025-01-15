package ec.com.sofka.mapper;

import ec.com.sofka.data.GameDocument;
import ec.com.sofka.gateway.dto.BoardDTO;
import ec.com.sofka.gateway.dto.GameDTO;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class DTOToDocumentMapper {
    public static final Function<Mono<GameDTO>, Mono<GameDocument>> toGame = account ->
            account.map(gameDTO -> {
                GameDocument gameDocument = new GameDocument();
                BeanUtils.copyProperties(gameDTO, gameDocument);
                return gameDocument;
            });
}
