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
    //TODO Validar dirección de movimientos según color y efctuar
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
        if (isInvalidDraughtsNotation(command.getDestination()) || isInvalidDraughtsNotation(command.getSource())) {
            throw new RuntimeException("Invalid chess notation");
        }
        Map<String, Map<String, Box>> boxes = board.getBoxes();
        String sourceColumn = command.getSource().substring(0, 1);
        String sourceRow = command.getSource().substring(1);
        String destinationColumn = command.getDestination().substring(0, 1);
        String destinationRow = command.getDestination().substring(1);
        Box sourceBox = boxes.get(sourceColumn).get(sourceRow);
        Box destinationBox = boxes.get(destinationColumn).get(destinationRow);
        if (sourceBox == null || sourceBox.getUsername() == null) {
            throw new RuntimeException("Invalid move: no piece to move");
        }
        if (!sourceBox.getUsername().equals(command.getPlayerUsername())) {
            throw new RuntimeException("Cannot move other user's pieces");
        }
        if (destinationBox.getUsername() != null) {
            throw new RuntimeException("Invalid move: destination is occupied");
        }
        String capturedColumn = null;
        String capturedRow = null;
        if (Math.abs(destinationColumn.charAt(0) - sourceColumn.charAt(0)) == 2 &&
                Math.abs(Integer.parseInt(destinationRow) - Integer.parseInt(sourceRow)) == 2) {
            capturedColumn = String.valueOf((char) ((sourceColumn.charAt(0) + destinationColumn.charAt(0)) / 2));
            capturedRow = String.valueOf((Integer.parseInt(sourceRow) + Integer.parseInt(destinationRow)) / 2);
            Box capturedBox = boxes.get(capturedColumn).get(capturedRow);
            if (capturedBox == null || capturedBox.getUsername() == null) {
                throw new RuntimeException("Invalid move: no piece to capture");
            }
            boxes.get(capturedColumn).put(capturedRow, new Box(null, null));
        }
        Box updatedSourceBox = new Box(null, null);
        Box updatedDestinationBox = new Box(sourceBox.getUsername(), sourceBox.getPieceState());
        boxes.get(sourceColumn).put(sourceRow, updatedSourceBox);
        boxes.get(destinationColumn).put(destinationRow, updatedDestinationBox);
        return board;
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

