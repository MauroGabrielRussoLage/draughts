package ec.com.sofka.entity.log;

import java.time.LocalDateTime;

//TODO Actualizar
public class GameEventLog {
    private String player1Username;
    private String player2Username;

    public GameEventLog(String player1Username, String player2Username) {
        this.player1Username = player1Username;
        this.player2Username = player2Username;
    }

    public GameEventLog() {
    }

    public String getPlayer1Username() {
        return player1Username;
    }

    public void setPlayer1Username(String player1Username) {
        this.player1Username = player1Username;
    }

    public String getPlayer2Username() {
        return player2Username;
    }

    public void setPlayer2Username(String player2Username) {
        this.player2Username = player2Username;
    }

    @Override
    public String toString() {
        return "GameEventLog{" +
                "player1Username='" + player1Username + '\'' +
                ", player2Username='" + player2Username + '\'' +
                '}';
    }
}