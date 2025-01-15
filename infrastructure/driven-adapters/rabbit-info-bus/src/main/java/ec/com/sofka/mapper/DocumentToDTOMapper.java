package ec.com.sofka.mapper;

import ec.com.sofka.data.GameDocument;
import ec.com.sofka.entity.board.value.object.Box;
import ec.com.sofka.gateway.dto.GameDTO;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;

public class DocumentToDTOMapper {
    public static final Function<Mono<GameDocument>, Mono<GameDTO>> toGame = account ->
            account.map(gameDocument -> {
                // Chequeos de null antes de usar los valores
                Map<String, Map<String, Box>> boxes = gameDocument.getBoard() != null ? gameDocument.getBoard().getBoxes() : null;
                String gameId = gameDocument.getId();
                String currentTurn = gameDocument.getCurrentTurn();
                String player1PieceColor = (gameDocument.getPlayer1() != null && gameDocument.getPlayer1().getPieceColor() != null)
                        ? gameDocument.getPlayer1().getPieceColor().getValue()
                        : null;
                String player1Name = (gameDocument.getPlayer1() != null && gameDocument.getPlayer1().getName() != null)
                        ? gameDocument.getPlayer1().getName().getValue()
                        : null;
                String player2PieceColor = (gameDocument.getPlayer2() != null && gameDocument.getPlayer2().getPieceColor() != null)
                        ? gameDocument.getPlayer2().getPieceColor().getValue()
                        : null;
                String player2Name = (gameDocument.getPlayer2() != null && gameDocument.getPlayer2().getName() != null)
                        ? gameDocument.getPlayer2().getName().getValue()
                        : null;
                LocalDateTime startDate = gameDocument.getStartDate();
                String status = gameDocument.getStatus();

                // Construcción del GameDTO
                GameDTO gameDTO = new GameDTO(
                        boxes,
                        gameId,
                        currentTurn,
                        player1PieceColor,
                        player1Name,
                        player2PieceColor,
                        player2Name,
                        startDate,
                        status
                );

                // Copia propiedades adicionales si es necesario
                BeanUtils.copyProperties(gameDocument, gameDTO);

                return gameDTO;
            });
}
