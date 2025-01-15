package ec.com.sofka.command;

import ec.com.sofka.generic.util.Command;

public class MovementCommand extends Command {
    private String playerUsername;
    private String source;
    private String destination;

    public MovementCommand(String aggregateId, String playerUsername, String source, String destination) {
        super(aggregateId);
        this.playerUsername = playerUsername;
        this.source = source;
        this.destination = destination;
    }

    public MovementCommand(String aggregateId) {
        super(aggregateId);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public static class Builder {
        private String playerUsername;
        private String source;
        private String destination;
        private String aggregateId;

        public Builder withPlayerId(String playerUsername) {
            this.playerUsername = playerUsername;
            return this;
        }

        public Builder withSource(String source) {
            this.source = source;
            return this;
        }

        public Builder withDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder withAggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
            return this;
        }

        public MovementCommand build() {
            return new MovementCommand(aggregateId, playerUsername, source, destination);
        }
    }
}
