package ec.com.sofka.aggregate.event;

import ec.com.sofka.entity.board.value.object.Box;
import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.EventEnum;

import java.util.Map;

public class BoardUpdated extends DomainEvent {
    private Map<String, Map<String, Box>> boxes;

    public BoardUpdated(Map<String, Map<String, Box>> boxes) {
        super(EventEnum.BOARD_UPDATED.name());
        this.boxes = boxes;
    }

    public BoardUpdated() {
        super(EventEnum.BOARD_UPDATED.name());
    }

    public Map<String, Map<String, Box>> getBoxes() {
        return boxes;
    }
}
