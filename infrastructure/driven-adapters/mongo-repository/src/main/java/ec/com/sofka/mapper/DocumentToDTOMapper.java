package ec.com.sofka.mapper;

import ec.com.sofka.data.GameDocument;
import ec.com.sofka.gateway.dto.BoardDTO;
import ec.com.sofka.gateway.dto.GameDTO;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class DocumentToDTOMapper {
    public static final Function<Mono<GameDocument>, Mono<GameDTO>> toGame = account ->
            account.map(gameDocument -> {
                GameDTO gameDTO = new GameDTO(
                        gameDocument.getBoard().getBoxes(),
                        gameDocument.getId(),
                        gameDocument.getCurrentTurn(),
                        gameDocument.getPlayer1().getPieceColor().getValue(),
                        gameDocument.getPlayer1().getName().getValue(),
                        gameDocument.getPlayer2().getPieceColor().getValue(),
                        gameDocument.getPlayer2().getName().getValue(),
                        gameDocument.getStartDate(),
                        gameDocument.getStatus()
                );
                BeanUtils.copyProperties(gameDocument, gameDTO);
                return gameDTO;
            });
}
