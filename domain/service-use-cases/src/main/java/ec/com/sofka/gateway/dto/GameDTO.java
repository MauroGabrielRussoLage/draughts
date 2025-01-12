package ec.com.sofka.gateway.dto;

import java.time.LocalDateTime;

public class GameDTO {
    private String player1Id;
    private String player2Id;
    private String boardId;
    private String status;
    private String currentTurn;
    private String winner;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    protected GameDTO(String player1Id, String player2Id, String boardId, String status, String currentTurn, String winner, LocalDateTime startDate, LocalDateTime endDate) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.boardId = boardId;
        this.status = status;
        this.currentTurn = currentTurn;
        this.winner = winner;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public GameDTO() {
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
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

    public String getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(String player1Id) {
        this.player1Id = player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(String player2Id) {
        this.player2Id = player2Id;
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
}
