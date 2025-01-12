package ec.com.sofka.entity.movement.event;

import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.EventEnum;

import java.time.LocalDateTime;

public class MovedPiece extends DomainEvent {
    private String gameId;
    private String playerId;
    private String source;
    private String destination;
    private LocalDateTime movementDate;
    private Boolean capturedPieces;

    public MovedPiece(String gameId, String playerId, String source, String destination, LocalDateTime movementDate, Boolean pieceCaptured) {
        super(EventEnum.MOVED_PIECE.name());
        this.gameId = gameId;
        this.playerId = playerId;
        this.source = source;
        this.destination = destination;
        this.movementDate = movementDate;
        this.capturedPieces = pieceCaptured;
    }

    public MovedPiece() {
        super(EventEnum.MOVED_PIECE.name());
    }

    public Boolean getCapturedPieces() {
        return capturedPieces;
    }

    public String getDestination() {
        return destination;
    }

    public String getGameId() {
        return gameId;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getSource() {
        return source;
    }
}
