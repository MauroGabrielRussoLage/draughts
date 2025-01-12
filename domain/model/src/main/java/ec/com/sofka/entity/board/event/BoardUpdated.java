package ec.com.sofka.entity.board.event;

import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.EventEnum;

public class BoardUpdated extends DomainEvent {
    private String[][] boxes;

    public BoardUpdated(String[][] boxes) {
        super(EventEnum.BOARD_UPDATED.name());
        this.boxes = boxes;
    }

    public BoardUpdated() {
        super(EventEnum.BOARD_UPDATED.name());
    }

    public String[][] getBoxes() {
        return boxes;
    }
}
