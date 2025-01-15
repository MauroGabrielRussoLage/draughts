package ec.com.sofka.aggregate.event;

import ec.com.sofka.entity.board.value.object.Box;
import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.EventEnum;

import java.time.LocalDateTime;
import java.util.Map;

public class PieceMoved extends DomainEvent {
    private String gameId;
    private String playerName;
    private String source;
    private String destination;
    private LocalDateTime movementDate;
    private Boolean capturedPieces;
    private Map<String, Map<String, Box>> board;

    public PieceMoved(String gameId, String playerName, String source, String destination, LocalDateTime movementDate, Boolean pieceCaptured, Map<String, Map<String, Box>> board) {
        super(EventEnum.PIECE_MOVED.name());
        this.gameId = gameId;
        this.playerName = playerName;
        this.source = source;
        this.destination = destination;
        this.movementDate = movementDate;
        this.capturedPieces = pieceCaptured;
        this.board = board;
    }

    public PieceMoved() {
        super(EventEnum.PIECE_MOVED.name());
    }

    public String getGameId() {
        return gameId;
    }

    public Boolean getCapturedPieces() {
        return capturedPieces;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getSource() {
        return source;
    }

    public Map<String, Map<String, Box>> getBoard() {
        return board;
    }
}
