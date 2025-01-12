package ec.com.sofka.aggregate;

import ec.com.sofka.aggregate.event.GameStarted;
import ec.com.sofka.aggregate.value.GameId;
import ec.com.sofka.aggregate.value.object.*;
import ec.com.sofka.entity.board.Board;
import ec.com.sofka.entity.player.Player;
import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.AggregateRoot;

import java.util.List;

public class Game extends AggregateRoot<GameId> {
    private Player player1;
    private Player player2;
    private Board board;
    private Status status;
    private CurrentTurn currentTurn;
    private Winner winner;
    private StartDate startDate;
    private EndDate endDate;

    public Game(final String id) {
        super(GameId.of(id));
        setSubscription(new GameHandler(this));
    }

    public Game() {
        super(new GameId());
        setSubscription(new GameHandler(this));
    }

    public Game(Player player1, Player player2, Board board, Status status, CurrentTurn currentTurn, StartDate startDate) {
        super(new GameId());
        setSubscription(new GameHandler(this));
        addEvent(new GameStarted(player1.getId().getValue(),
                player2.getId().getValue(),
                board.getId().getValue(),
                status.getValue(),
                currentTurn.getValue(),
                startDate.getValue())).apply();
    }

    public static Game from(final String id, List<DomainEvent> events) {
        Game game = new Game(id);
        events.stream()
                .filter(event -> id.equals(event.getAggregateRootId()))
                .forEach((event) -> game.addEvent(event).apply());
        return game;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public CurrentTurn getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(CurrentTurn currentTurn) {
        this.currentTurn = currentTurn;
    }

    public EndDate getEndDate() {
        return endDate;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }
}
