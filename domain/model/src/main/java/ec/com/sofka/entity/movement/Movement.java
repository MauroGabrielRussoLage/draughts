package ec.com.sofka.entity.movement;

import ec.com.sofka.aggregate.value.GameId;
import ec.com.sofka.entity.movement.value.MovementId;
import ec.com.sofka.entity.movement.value.object.CapturedPieces;
import ec.com.sofka.entity.movement.value.object.Coordinate;
import ec.com.sofka.entity.movement.value.object.MovementDate;
import ec.com.sofka.entity.player.value.PlayerId;
import ec.com.sofka.generic.util.Entity;

public class Movement extends Entity<MovementId> {
    private GameId gameId;
    private PlayerId playerId;
    private Coordinate source;
    private Coordinate destination;
    private MovementDate movementDate;
    private CapturedPieces capturedPieces;

    public Movement(GameId gameId, PlayerId playerId, Coordinate source, Coordinate destination, MovementDate movementDate, CapturedPieces capturedPieces) {
        super(new MovementId());
        this.gameId = gameId;
        this.playerId = playerId;
        this.source = source;
        this.destination = destination;
        this.movementDate = movementDate;
        this.capturedPieces = capturedPieces;
    }

    public Movement() {
        super(null);
    }

    public CapturedPieces getCapturedPieces() {
        return capturedPieces;
    }

    public void setCapturedPieces(CapturedPieces capturedPieces) {
        this.capturedPieces = capturedPieces;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public void setDestination(Coordinate destination) {
        this.destination = destination;
    }

    public GameId getGameId() {
        return gameId;
    }

    public void setGameId(GameId gameId) {
        this.gameId = gameId;
    }

    public MovementDate getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(MovementDate movementDate) {
        this.movementDate = movementDate;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public void setPlayerId(PlayerId playerId) {
        this.playerId = playerId;
    }

    public Coordinate getSource() {
        return source;
    }

    public void setSource(Coordinate source) {
        this.source = source;
    }
}
