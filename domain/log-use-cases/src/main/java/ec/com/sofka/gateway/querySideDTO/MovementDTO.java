package ec.com.sofka.gateway.querySideDTO;

import ec.com.sofka.entity.board.value.object.Box;

import java.time.LocalDateTime;
import java.util.Map;

public class MovementDTO {
    private String gameId;
    private String playerName;
    private String source;
    private String destination;
    private LocalDateTime movementDate;
    private Boolean capturedPieces;
    private Map<String, Map<String, Box>> board;

    public MovementDTO(Map<String, Map<String, Box>> board, Boolean capturedPieces, String destination, String gameId, LocalDateTime movementDate, String playerName, String source) {
        this.board = board;
        this.capturedPieces = capturedPieces;
        this.destination = destination;
        this.gameId = gameId;
        this.movementDate = movementDate;
        this.playerName = playerName;
        this.source = source;
    }

    public MovementDTO() {}

    public Map<String, Map<String, Box>> getBoard() {
        return board;
    }

    public void setBoard(Map<String, Map<String, Box>> board) {
        this.board = board;
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
