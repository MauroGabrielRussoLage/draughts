package ec.com.sofka.aggregate.event;

import ec.com.sofka.entity.board.value.object.Box;
import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.EventEnum;

import java.time.LocalDateTime;
import java.util.Map;

public class GameStarted extends DomainEvent {
    private String player1Name;
    private String player1Color;
    private String player2Name;
    private String player2Color;
    private String status;
    private String currentTurn;
    private LocalDateTime startDate;
    private Map<String, Map<String, Box>> board;

    public GameStarted(String currentTurn, String player1Color, String player1Name, String player2Color, String player2Name, LocalDateTime startDate, String status, Map<String, Map<String, Box>> board) {
        super(EventEnum.GAME_STARTED.name());
        this.currentTurn = currentTurn;
        this.player1Color = player1Color;
        this.player1Name = player1Name;
        this.player2Color = player2Color;
        this.player2Name = player2Name;
        this.startDate = startDate;
        this.status = status;
        this.board = board;
    }

    public GameStarted() {
        super(EventEnum.GAME_STARTED.name());
    }

    public String getPlayer2Color() {
        return player2Color;
    }

    public String getPlayer1Color() {
        return player1Color;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public String getStatus() {
        return status;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Map<String, Map<String, Box>> getBoard() {
        return board;
    }
}
