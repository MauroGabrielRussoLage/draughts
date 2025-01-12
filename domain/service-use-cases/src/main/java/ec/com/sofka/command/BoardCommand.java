package ec.com.sofka.command;

import ec.com.sofka.generic.util.Command;

public class BoardCommand extends Command {
    private String[][] boxes;

    public BoardCommand(String aggregateId, String[][] boxes) {
        super(aggregateId);
        this.boxes = boxes;
    }

    public BoardCommand(String aggregateId) {
        super(aggregateId);
    }

    public String[][] getBoxes() {
        return boxes;
    }

    public void setBoxes(String[][] boxes) {
        this.boxes = boxes;
    }

    public static class Builder {
        private String[][] boxes;
        private String aggregateId;

        public Builder withBoxes(String[][] boxes) {
            this.boxes = boxes;
            return this;
        }

        public Builder withAggregateId(String aggregateId) {
            this.aggregateId = aggregateId;
            return this;
        }

        public BoardCommand build() {
            return new BoardCommand(aggregateId, boxes);
        }
    }
}
