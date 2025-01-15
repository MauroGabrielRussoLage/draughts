package ec.com.sofka.aggregate;

import ec.com.sofka.aggregate.event.GameStarted;
import ec.com.sofka.aggregate.value.object.CurrentTurn;
import ec.com.sofka.aggregate.value.object.StartDate;
import ec.com.sofka.aggregate.value.object.Status;
import ec.com.sofka.entity.board.Board;
import ec.com.sofka.entity.movement.Movement;
import ec.com.sofka.aggregate.event.PieceMoved;
import ec.com.sofka.entity.movement.value.object.CapturedPieces;
import ec.com.sofka.entity.movement.value.object.Coordinate;
import ec.com.sofka.entity.movement.value.object.MovementDate;
import ec.com.sofka.entity.player.Player;
import ec.com.sofka.entity.player.value.object.Name;
import ec.com.sofka.entity.player.value.object.PieceColor;
import ec.com.sofka.generic.domain.DomainActionsContainer;

public class GameHandler extends DomainActionsContainer {
    public GameHandler(Game game) {
        addDomainActions((GameStarted event) -> {
            game.player1 = new Player(Name.of(event.getPlayer1Name()), PieceColor.of(event.getPlayer1Color()));
            game.player2 = new Player(Name.of(event.getPlayer2Name()), PieceColor.of(event.getPlayer2Color()));
            game.status = new Status(event.getStatus());
            game.currentTurn = new CurrentTurn(event.getCurrentTurn());
            game.startDate = new StartDate(event.getStartDate());
            game.board = new Board(event.getBoard());
        });
        addDomainActions((PieceMoved event) -> {
            game.movements.add(new Movement(
                    Name.of(event.getPlayerName()),
                    Coordinate.of(event.getSource()),
                    Coordinate.of(event.getDestination()),
                    MovementDate.of(event.getMovementDate()),
                    CapturedPieces.of(event.getCapturedPieces())
            ));
            game.board = new Board(event.getBoard());
        });
    }
}