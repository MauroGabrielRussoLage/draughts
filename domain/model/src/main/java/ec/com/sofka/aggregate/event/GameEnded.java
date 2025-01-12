package ec.com.sofka.aggregate.event;

import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.EventEnum;

import java.time.LocalDateTime;

public class GameEnded extends DomainEvent {
    private String player1Id;
    private String player2Id;
    private String boardId;
    private String status;
    private String currentTurn;
    private String winner;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public GameEnded(String player1Id, String player2Id, String boardId, String status, String currentTurn, String winner, LocalDateTime startDate, LocalDateTime endDate) {
        super(EventEnum.GAME_ENDED.name());
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.boardId = boardId;
        this.status = status;
        this.currentTurn = currentTurn;
        this.winner = winner;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public GameEnded() {
        super(EventEnum.GAME_ENDED.name());
    }

    public String getBoardId() {
        return boardId;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public String getPlayer1Id() {
        return player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getWinner() {
        return winner;
    }
}
