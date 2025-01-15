package ec.com.sofka.useCase;


import ec.com.sofka.entity.board.value.object.Box;
import ec.com.sofka.gateway.querySideDTO.GameDTO;
import ec.com.sofka.gateway.querySideDTO.MovementDTO;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FinalizedGameVerificationUseCase {

    private final UpdateViewGameUseCase updateViewGameUseCase;

    public FinalizedGameVerificationUseCase(UpdateViewGameUseCase updateViewGameUseCase) {
        this.updateViewGameUseCase = updateViewGameUseCase;
    }

    public Mono<Void> apply(Mono<MovementDTO> movementDTOMono) {
        return movementDTOMono.flatMap(movementDTO -> {
            Optional<String> winner = checkGameStatus(movementDTO.getBoard());
            GameDTO gameDTO = new GameDTO();
            gameDTO.setGameId(movementDTO.getGameId());
            gameDTO.setBoard(movementDTO.getBoard());
            List<MovementDTO> movementDTOList = new ArrayList<>();
            movementDTOList.add(movementDTO);
            gameDTO.setMovements(movementDTOList);
            if (winner.isPresent()) {
                gameDTO.setStatus("Game finalized");
                gameDTO.setWinner(winner.get());
                gameDTO.setEndDate(LocalDateTime.now());
            }
            return updateViewGameUseCase.apply(Mono.just(gameDTO));
        }).then();
    }

    public Optional<String> checkGameStatus(Map<String, Map<String, Box>> board) {
        int whitePieces = 0;
        int blackPieces = 0;
        boolean whiteCanMove = false;
        boolean blackCanMove = false;
        for (Map.Entry<String, Map<String, Box>> columnEntry : board.entrySet()) {
            String column = columnEntry.getKey();
            Map<String, Box> rows = columnEntry.getValue();
            for (Map.Entry<String, Box> rowEntry : rows.entrySet()) {
                String row = rowEntry.getKey();
                Box box = rowEntry.getValue();
                if (box == null) {
                    continue;
                }
                if (box.getUsername() == null && box.getPieceState() == null) {
                    continue;
                }
                if ("WHITE".equals(box.getPieceState())) {
                    whitePieces++;
                    if (canMove(board, column, row, "WHITE", box)) {
                        whiteCanMove = true;
                    }
                } else if ("BLACK".equals(box.getPieceState())) {
                    blackPieces++;
                    if (canMove(board, column, row, "BLACK", box)) {
                        blackCanMove = true;
                    }
                }
            }
        }
        if (!whiteCanMove && !blackCanMove && whitePieces == blackPieces) {
            return Optional.of("DRAW");
        }
        if (whitePieces == 0 || !whiteCanMove) {
            return Optional.of("BLACK");
        } else if (blackPieces == 0 || !blackCanMove) {
            return Optional.of("WHITE");
        }
        return Optional.empty();
    }

    private boolean canMove(Map<String, Map<String, Box>> board, String column, String row, String pieceColor, Box box) {
        int[][] directions = "KING".equals(box.getPieceState())
                ? new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}
                : "WHITE".equals(pieceColor)
                ? new int[][]{{1, 1}, {1, -1}}
                : new int[][]{{-1, 1}, {-1, -1}};
        for (int[] direction : directions) {
            String newColumn = String.valueOf((char) (column.charAt(0) + direction[0]));
            String newRow = String.valueOf(Integer.parseInt(row) + direction[1]);
            Map<String, Box> columnBoxes = board.get(newColumn);
            if (columnBoxes != null) {
                Box targetBox = columnBoxes.get(newRow);
                if (targetBox != null && targetBox.getUsername() == null && targetBox.getPieceState() == null) {
                    return true;
                }
            }
        }
        return false;
    }
}
