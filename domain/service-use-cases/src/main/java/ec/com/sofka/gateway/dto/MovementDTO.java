package ec.com.sofka.gateway.dto;

import java.time.LocalDateTime;

public class MovementDTO {
    private String gameId;
    private String playerId;
    private String source;
    private String destination;
    private LocalDateTime movementDate;
    private Boolean capturedPieces;

    public MovementDTO(String gameId, String playerId, String source, String destination, LocalDateTime movementDate, Boolean capturedPieces) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.source = source;
        this.destination = destination;
        this.movementDate = movementDate;
        this.capturedPieces = capturedPieces;
    }

    public MovementDTO() {
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
}
