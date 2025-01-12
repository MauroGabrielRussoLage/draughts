package ec.com.sofka.command;

import ec.com.sofka.generic.util.Command;

public class GameCommand extends Command {
    private String player1Username;
    private String player2Username;
    private String loggedUsername;

    public GameCommand(String aggregateId, String player1Username, String player2Username, String loggedUsername) {
        super(aggregateId);
        this.player1Username = player1Username;
        this.player2Username = player2Username;
        this.loggedUsername = loggedUsername;
    }

    public GameCommand(String aggregateId) {
        super(aggregateId);
    }

    public String getPlayer1Username() {
        return player1Username;
    }

    public void setPlayer1Username(String player1Id) {
        this.player1Username = player1Id;
    }

    public String getPlayer2Username() {
        return player2Username;
    }

    public void setPlayer2Username(String player2Username) {
        this.player2Username = player2Username;
    }

    public String getLoggedUsername() {
        return loggedUsername;
    }

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }

    public static class Builder {
        private String player1Username;
        private String player2Username;
        private String loggedUsername;
        private String aggregateId;

        public Builder withPlayer1Id(String player1Username) {
            this.player1Username = player1Username;
            return this;
        }

        public Builder withPlayer2Id(String player2Username) {
            this.player2Username = player2Username;
            return this;
        }

        public Builder withLoggedUsername(String loggedUsername) {
            this.loggedUsername = loggedUsername;
            return this;
        }

        public Builder withAggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
            return this;
        }

        public GameCommand build() {
            return new GameCommand(aggregateId, player1Username, player2Username, loggedUsername);
        }
    }
}
