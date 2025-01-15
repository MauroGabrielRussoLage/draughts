package ec.com.sofka.useCase;

import ec.com.sofka.aggregate.Game;
import ec.com.sofka.aggregate.value.object.CurrentTurn;
import ec.com.sofka.command.MovementCommand;
import ec.com.sofka.entity.board.Board;
import ec.com.sofka.entity.board.value.object.Box;
import ec.com.sofka.entity.movement.value.object.CapturedPieces;
import ec.com.sofka.entity.movement.value.object.Coordinate;
import ec.com.sofka.entity.movement.value.object.MovementDate;
import ec.com.sofka.entity.player.value.object.Name;
import ec.com.sofka.entity.player.value.object.PieceColor;
import ec.com.sofka.gateway.BusMessage;
import ec.com.sofka.gateway.EventStore;
import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.response.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MovementUseCase {
    private final EventStore repository;
    private final BusMessage busMessage;

    public MovementUseCase(EventStore repository, BusMessage busMessage) {
        this.repository = repository;
        this.busMessage = busMessage;
    }

    public Mono<Object> apply(Mono<MovementCommand> command) {
        return command.flatMap(request ->
                repository.findAggregate(Mono.just(request.getAggregateId()))
                        .collectList()
                        .flatMap(events -> processCommand(request, events))
        );
    }

    private Mono<Object> processCommand(MovementCommand request, List<DomainEvent> events) {
        Game game = Game.from(request.getAggregateId(), events);
        PieceColor currentPlayerPieceColor = game.getCurrentTurn().getValue().equals(game.getPlayer1().getName().getValue())
                ? game.getPlayer1().getPieceColor()
                : game.getPlayer2().getPieceColor();
        game.addMovement(
                detectCapturedPiece(game.getBoard(), request),
                Coordinate.of(request.getDestination()),
                MovementDate.of(LocalDateTime.now()),
                Name.of(request.getPlayerUsername()),
                Coordinate.of(request.getSource()),
                updateBoard(game.getBoard(), request, currentPlayerPieceColor)
        );
        updateCurrentTurn(game);
        return saveAndPublishEvents(game)
                .then(Mono.defer(() -> {
                    game.markEventsAsCommitted();
                    return Mono.just(new Response("Move applied successfully"));
                }));
    }

    private void updateCurrentTurn(Game game) {
        String currentTurn = game.getCurrentTurn().getValue();
        String nextTurn = currentTurn.equals(game.getPlayer1().getName().getValue())
                ? game.getPlayer2().getName().getValue()
                : game.getPlayer1().getName().getValue();
        game.setCurrentTurn(CurrentTurn.of(nextTurn));
    }

    private Mono<Void> saveAndPublishEvents(Game game) {
        return Flux.fromIterable(game.getUncommittedEvents())
                .flatMap(event ->
                        repository.save(Mono.just(event))
                                .then(busMessage.sendMsg(Mono.just(event)))
                )
                .then();
    }

    private CapturedPieces detectCapturedPiece(Board board, MovementCommand request) {
        if (Math.abs(request.getDestination().charAt(0) - request.getSource().charAt(0)) == 2 &&
                Math.abs(Integer.parseInt(request.getDestination().substring(1)) -
                        Integer.parseInt(request.getSource().substring(1))) == 2) {
            String capturedColumn = String.valueOf((char) ((request.getSource().charAt(0) + request.getDestination().charAt(0)) / 2));
            String capturedRow = String.valueOf((Integer.parseInt(request.getSource().substring(1)) +
                    Integer.parseInt(request.getDestination().substring(1))) / 2);
            Map<String, Map<String, Box>> boxes = board.getBoxes();
            Box capturedBox = boxes.get(capturedColumn).get(capturedRow);
            return CapturedPieces.of(capturedBox != null && capturedBox.getUsername() != null);
        }
        return CapturedPieces.of(false);
    }

    private Board updateBoard(Board board, MovementCommand command, PieceColor pieceColor) {
        validateNotation(command);
        Map<String, Map<String, Box>> boxes = board.getBoxes();
        String sourceColumn = getColumn(command.getSource());
        String sourceRow = getRow(command.getSource());
        String destinationColumn = getColumn(command.getDestination());
        String destinationRow = getRow(command.getDestination());
        Box sourceBox = getBox(boxes, sourceColumn, sourceRow);
        Box destinationBox = getBox(boxes, destinationColumn, destinationRow);
        validateSourceBox(sourceBox, command);
        validateDestinationBox(destinationBox);
        validateDiagonalMove(sourceColumn, sourceRow, destinationColumn, destinationRow);
        if (!"KING".equals(sourceBox.getPieceState())) {
            validateMoveDirection(pieceColor, sourceRow, destinationRow);
        }
        handleCapture(boxes, sourceColumn, sourceRow, destinationColumn, destinationRow);
        updateBoxes(boxes, sourceColumn, sourceRow, destinationColumn, destinationRow, sourceBox);
        return board;
    }

    private void validateNotation(MovementCommand command) {
        if (isInvalidDraughtsNotation(command.getDestination()) || isInvalidDraughtsNotation(command.getSource())) {
            throw new IllegalArgumentException("Invalid chess notation");
        }
    }

    private String getColumn(String position) {
        return position.substring(0, 1);
    }

    private String getRow(String position) {
        return position.substring(1);
    }

    private Box getBox(Map<String, Map<String, Box>> boxes, String column, String row) {
        Map<String, Box> columnBoxes = boxes.get(column);
        if (columnBoxes == null) {
            throw new IllegalArgumentException("Invalid move: column does not exist");
        }
        Box box = columnBoxes.get(row);
        if (box == null) {
            throw new IllegalArgumentException("Invalid move: row does not exist");
        }
        return box;
    }

    private void validateSourceBox(Box sourceBox, MovementCommand command) {
        if (sourceBox == null || sourceBox.getUsername() == null) {
            throw new IllegalArgumentException("Invalid move: no piece to move");
        }
        if (!sourceBox.getUsername().equals(command.getPlayerUsername())) {
            throw new IllegalArgumentException("Cannot move other user's pieces");
        }
    }

    private void validateDestinationBox(Box destinationBox) {
        if (destinationBox.getUsername() != null) {
            throw new IllegalArgumentException("Invalid move: destination is occupied");
        }
    }

    private void validateDiagonalMove(String sourceColumn, String sourceRow, String destinationColumn, String destinationRow) {
        int rowDifference = Integer.parseInt(destinationRow) - Integer.parseInt(sourceRow);
        int columnDifference = destinationColumn.charAt(0) - sourceColumn.charAt(0);
        if (Math.abs(rowDifference) != Math.abs(columnDifference)) {
            throw new IllegalArgumentException("Invalid move: pieces must move diagonally");
        }
    }

    private void validateMoveDirection(PieceColor pieceColor, String sourceRow, String destinationRow) {
        int rowDifference = Integer.parseInt(destinationRow) - Integer.parseInt(sourceRow);
        if (pieceColor.getValue().equals("WHITE") && rowDifference <= 0) {
            throw new IllegalArgumentException("Invalid move: WHITE pieces must move forward");
        }
        if (pieceColor.getValue().equals("BLACK") && rowDifference >= 0) {
            throw new IllegalArgumentException("Invalid move: BLACK pieces must move forward");
        }
    }

    private void handleCapture(Map<String, Map<String, Box>> boxes, String sourceColumn, String sourceRow, String destinationColumn, String destinationRow) {
        int columnDifference = Math.abs(destinationColumn.charAt(0) - sourceColumn.charAt(0));
        int rowDifference = Math.abs(Integer.parseInt(destinationRow) - Integer.parseInt(sourceRow));
        if (columnDifference == 2 && rowDifference == 2) {
            String capturedColumn = String.valueOf((char) ((sourceColumn.charAt(0) + destinationColumn.charAt(0)) / 2));
            String capturedRow = String.valueOf((Integer.parseInt(sourceRow) + Integer.parseInt(destinationRow)) / 2);
            Box capturedBox = getBox(boxes, capturedColumn, capturedRow);
            if (capturedBox.getUsername() == null) {
                throw new IllegalArgumentException("Invalid move: no piece to capture");
            }
            boxes.get(capturedColumn).put(capturedRow, new Box(null, null));
        }
    }

    private void updateBoxes(Map<String, Map<String, Box>> boxes, String sourceColumn, String sourceRow, String destinationColumn, String destinationRow, Box sourceBox) {
        boxes.get(sourceColumn).put(sourceRow, new Box(null, null));
        boxes.get(destinationColumn).put(destinationRow, new Box(sourceBox.getUsername(), sourceBox.getPieceState()));
    }

    public boolean isInvalidDraughtsNotation(String position) {
        String regex = "^[A-H][1-8]$";
        if (!position.matches(regex)) {
            return true;
        }
        char column = position.charAt(0);
        int row = Character.getNumericValue(position.charAt(1));
        int columnIndex = column - 'A';
        return (columnIndex + row) % 2 == 0;
    }
}

