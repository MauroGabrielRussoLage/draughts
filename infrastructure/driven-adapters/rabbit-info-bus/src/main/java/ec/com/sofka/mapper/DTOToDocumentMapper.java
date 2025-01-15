package ec.com.sofka.mapper;

import ec.com.sofka.data.GameDocument;

import ec.com.sofka.entity.board.Board;
import ec.com.sofka.entity.player.Player;
import ec.com.sofka.entity.player.value.object.Name;
import ec.com.sofka.entity.player.value.object.PieceColor;
import ec.com.sofka.gateway.querySideDTO.GameDTO;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class DTOToDocumentMapper {
    public static final Function<Mono<GameDTO>, Mono<GameDocument>> toGame = account ->
            account.map(gameDTO -> {
                GameDocument gameDocument = new GameDocument();
                gameDocument.setBoard(new Board(gameDTO.getBoard()));
                gameDocument.setId(gameDTO.getGameId());
                gameDocument.setCurrentTurn(gameDTO.getCurrentTurn());
                gameDocument.setStatus(gameDTO.getStatus());
                gameDocument.setStartDate(gameDTO.getStartDate());
                gameDocument.setPlayer2(
                        new Player(
                                Name.of(
                                        gameDTO.getPlayer2Name()),
                                PieceColor.of(
                                        gameDTO.getPlayer2Color())));
                gameDocument.setPlayer1(new Player(
                        Name.of(
                                gameDTO.getPlayer1Name()),
                        PieceColor.of(
                                gameDTO.getPlayer1Color())));
                BeanUtils.copyProperties(gameDTO, gameDocument);
                return gameDocument;
            });
}
