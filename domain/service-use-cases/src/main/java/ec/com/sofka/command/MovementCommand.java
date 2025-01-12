package ec.com.sofka.command;

import ec.com.sofka.generic.util.Command;

import java.time.LocalDateTime;

public class MovementCommand extends Command {
    private String gameId;
    private String playerId;
    private String source;
    private String destination;
    private LocalDateTime movementDate;
    private Boolean capturedPieces;

    public MovementCommand(String aggregateId, String gameId, String playerId, String source, String destination, LocalDateTime movementDate, Boolean capturedPieces) {
        super(aggregateId);
        this.gameId = gameId;
        this.playerId = playerId;
        this.source = source;
        this.destination = destination;
        this.movementDate = movementDate;
        this.capturedPieces = capturedPieces;
    }

    public MovementCommand(String aggregateId) {
        super(aggregateId);
    }

    public Boolean getCapturedPieces() {
        return capturedPieces;
    }

    public void setCapturedPieces(Boolean capturedPieces) {
        this.capturedPieces = capturedPieces;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public static class Builder {
        private String gameId;
        private String playerId;
        private String source;
        private String destination;
        private LocalDateTime movementDate;
        private Boolean capturedPieces;
        private String aggregateId;

        public Builder withGameId(String gameId) {
            this.gameId = gameId;
            return this;
        }

        public Builder withPlayerId(String playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder withSource(String source) {
            this.source = source;
            return this;
        }

        public Builder withDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder withAggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
            return this;
        }

        public MovementCommand build() {
            return new MovementCommand(aggregateId, gameId, playerId, source, destination, movementDate, capturedPieces);
        }
    }
}
