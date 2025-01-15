package ec.com.sofka.useCase;

import ec.com.sofka.command.BoardGenerationCommand;
import ec.com.sofka.command.GameCommand;
import ec.com.sofka.entity.board.Board;
import ec.com.sofka.entity.board.value.object.Box;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class BoardGenerationUseCase {
    public Mono<Board> apply(Mono<BoardGenerationCommand> command) {
        return command.flatMap(boardGenerationCommand -> {
            Map<String, Map<String, Box>> board = new HashMap<>();
            for (char col = 'A'; col <= 'H'; col++) {
                String column = String.valueOf(col);
                board.put(column, new HashMap<>());
                for (int row = 1; row <= 8; row++) {
                    String rowKey = String.valueOf(row);
                    if ((col - 'A' + row) % 2 != 0) {
                        if (row <= 3) {
                            if (boardGenerationCommand.getPlayer1PieceColor().equalsIgnoreCase("white"))
                                board.get(column).put(rowKey, new Box(boardGenerationCommand
                                        .getPlayer1Username(), "PAWN"));
                            else
                                board.get(column).put(rowKey, new Box(boardGenerationCommand
                                        .getPlayer2Username(), "PAWN"));
                        } else if (row >= 6) {
                            if (boardGenerationCommand.getPlayer2PieceColor().equalsIgnoreCase("black"))
                                board.get(column).put(rowKey, new Box(boardGenerationCommand
                                        .getPlayer2Username(), "PAWN"));
                            else
                                board.get(column).put(rowKey, new Box(boardGenerationCommand
                                        .getPlayer1Username(), "PAWN"));
                        } else {
                            board.get(column).put(rowKey, new Box(null, null));
                        }
                    } else {
                        board.get(column).put(rowKey, null);
                    }
                }
            }
            return Mono.just(new Board(board));
        });
    }
}