package ec.com.sofka.command;

import ec.com.sofka.generic.util.Command;

public class BoardGenerationCommand extends Command {
    private String player1Username;
    private String player1PieceColor;
    private String player2Username;
    private String player2PieceColor;

    public BoardGenerationCommand(String aggregateId, String player1PieceColor, String player1Username, String player2PieceColor, String player2Username) {
        super(aggregateId);
        this.player1PieceColor = player1PieceColor;
        this.player1Username = player1Username;
        this.player2PieceColor = player2PieceColor;
        this.player2Username = player2Username;
    }

    public BoardGenerationCommand(String aggregateId) {
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

    public String getPlayer1PieceColor() {
        return player1PieceColor;
    }

    public void setPlayer1PieceColor(String player1PieceColor) {
        this.player1PieceColor = player1PieceColor;
    }

    public String getPlayer2PieceColor() {
        return player2PieceColor;
    }

    public void setPlayer2PieceColor(String player2PieceColor) {
        this.player2PieceColor = player2PieceColor;
    }

    public static class Builder {
        private String player1Username;
        private String player1PieceColor;
        private String player2Username;
        private String player2PieceColor;
        private String aggregateId;

        public Builder withPlayer1Id(String player1Username) {
            this.player1Username = player1Username;
            return this;
        }

        public Builder withPlayer1PieceColor(String player1PieceColor) {
            this.player1PieceColor = player1PieceColor;
            return this;
        }

        public Builder withPlayer2Id(String player2Username) {
            this.player2Username = player2Username;
            return this;
        }

        public Builder withPlayer2PieceColor(String player2PieceColor) {
            this.player2PieceColor = player2PieceColor;
            return this;
        }

        public Builder withAggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
            return this;
        }

        public BoardGenerationCommand build() {
            return new BoardGenerationCommand(aggregateId, player1PieceColor, player1Username, player2PieceColor, player2Username);
        }
    }
}
