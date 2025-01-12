package ec.com.sofka.command;

import ec.com.sofka.generic.util.Command;

public class PlayerCommand extends Command {
    private String name;
    private String pieceColor;

    public PlayerCommand(String aggregateId, String name, String pieceColor) {
        super(aggregateId);
        this.name = name;
        this.pieceColor = pieceColor;
    }

    public PlayerCommand(String aggregateId) {
        super(aggregateId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(String pieceColor) {
        this.pieceColor = pieceColor;
    }

    public static class Builder {
        private String name;
        private String pieceColor;
        private String aggregateId;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPieceColor(String pieceColor) {
            this.pieceColor = pieceColor;
            return this;
        }

        public Builder withAggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
            return this;
        }

        public PlayerCommand build() {
            return new PlayerCommand(aggregateId, name, pieceColor);
        }
    }
}
