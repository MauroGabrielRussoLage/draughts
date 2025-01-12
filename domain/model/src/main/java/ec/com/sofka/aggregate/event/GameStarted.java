package ec.com.sofka.aggregate.event;

import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.EventEnum;

import java.time.LocalDateTime;

public class GameStarted extends DomainEvent {
    private String player1Id;
    private String player2Id;
    private String boardId;
    private String staus;
    private String currentTurn;
    private LocalDateTime startDate;

    public GameStarted(String player1Id, String player2Id, String boardId, String staus, String currentTurn, LocalDateTime startDate) {
        super(EventEnum.GAME_STARTED.name());
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.boardId = boardId;
        this.staus = staus;
        this.currentTurn = currentTurn;
        this.startDate = startDate;
    }

    public GameStarted() {
        super(EventEnum.GAME_STARTED.name());
    }

    public String getBoardId() {
        return boardId;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public String getStaus() {
        return staus;
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
}
