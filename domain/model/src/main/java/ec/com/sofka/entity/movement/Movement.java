package ec.com.sofka.entity.movement;

import ec.com.sofka.entity.movement.value.MovementId;
import ec.com.sofka.entity.movement.value.object.CapturedPieces;
import ec.com.sofka.entity.movement.value.object.Coordinate;
import ec.com.sofka.entity.movement.value.object.MovementDate;
import ec.com.sofka.entity.player.value.object.Name;
import ec.com.sofka.generic.util.Entity;

public class Movement extends Entity<MovementId> {
    private Name playerName;
    private Coordinate source;
    private Coordinate destination;
    private MovementDate movementDate;
    private CapturedPieces capturedPieces;

    public Movement(Name playerName, Coordinate source, Coordinate destination, MovementDate movementDate, CapturedPieces capturedPieces) {
        super(new MovementId());
        this.playerName = playerName;
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

    public MovementDate getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(MovementDate movementDate) {
        this.movementDate = movementDate;
    }

    public Name getPlayerName() {
        return playerName;
    }

    public void setPlayerName(Name playerName) {
        this.playerName = playerName;
    }

    public Coordinate getSource() {
        return source;
    }

    public void setSource(Coordinate source) {
        this.source = source;
    }
}
