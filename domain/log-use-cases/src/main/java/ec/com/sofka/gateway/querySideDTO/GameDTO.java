package ec.com.sofka.gateway.querySideDTO;

import ec.com.sofka.entity.board.value.object.Box;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class GameDTO {
    private String gameId;
    private String player1Name;
    private String player1Color;
    private String player2Name;
    private String player2Color;
    private Map<String, Map<String, Box>> board;
    private String status;
    private String currentTurn;
    private String winner;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<MovementDTO> movements;

    public GameDTO(Map<String, Map<String, Box>> board,
                   String currentTurn,
                   LocalDateTime endDate,
                   String gameId,
                   String player1Color,
                   String player1Name,
                   String player2Color,
                   String player2Name,
                   LocalDateTime startDate,
                   String status,
                   String winner) {
        this.board = board;
        this.currentTurn = currentTurn;
        this.endDate = endDate;
        this.gameId = gameId;
        this.player1Color = player1Color;
        this.player1Name = player1Name;
        this.player2Color = player2Color;
        this.player2Name = player2Name;
        this.startDate = startDate;
        this.status = status;
        this.winner = winner;
    }

    public GameDTO(Map<String, Map<String, Box>> board,
                   String currentTurn,
                   String gameId,
                   String player1Color,
                   String player1Name,
                   String player2Color,
                   String player2Name,
                   LocalDateTime startDate,
                   String status) {
        this.board = board;
        this.currentTurn = currentTurn;
        this.gameId = gameId;
        this.player1Color = player1Color;
        this.player1Name = player1Name;
        this.player2Color = player2Color;
        this.player2Name = player2Name;
        this.startDate = startDate;
        this.status = status;
    }

    public GameDTO() {
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Map<String, Map<String, Box>> getBoard() {
        return board;
    }

    public void setBoard(Map<String, Map<String, Box>> board) {
        this.board = board;
    }

    public String getPlayer1Color() {
        return player1Color;
    }

    public void setPlayer1Color(String player1Color) {
        this.player1Color = player1Color;
    }

    public String getPlayer2Color() {
        return player2Color;
    }

    public void setPlayer2Color(String player2Color) {
        this.player2Color = player2Color;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public List<MovementDTO> getMovements() {
        return movements;
    }

    public void setMovements(List<MovementDTO> movements) {
        this.movements = movements;
    }
}
