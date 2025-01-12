package ec.com.sofka.entity.board.event;

import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.EventEnum;

public class BoardCreated extends DomainEvent {
    private String[][] boxes;

    public BoardCreated(String[][] boxes) {
        super(EventEnum.BOARD_CREATED.name());
        this.boxes = boxes;
    }

    public BoardCreated() {
        super(EventEnum.BOARD_CREATED.name());
    }

    public String[][] getBoxes() {
        return boxes;
    }
}
