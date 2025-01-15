package ec.com.sofka.entity.board;

import ec.com.sofka.entity.board.value.BoardId;
import ec.com.sofka.entity.board.value.object.Box;
import ec.com.sofka.generic.util.Entity;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Map;

public class Board extends Entity<BoardId> {
    private Map<String, Map<String, Box>> boxes;

    public Board(Map<String, Map<String, Box>> boxes) {
        super(new BoardId());
        this.boxes = boxes;
    }

    @PersistenceConstructor
    public Board(BoardId id, Map<String, Map<String, Box>> boxes) {
        super(id);
        this.boxes = boxes;
    }

    public Board() {
        super(null);
    }

    public Map<String, Map<String, Box>> getBoxes() {
        return boxes;
    }

    public void setBoxes(Map<String, Map<String, Box>> boxes) {
        this.boxes = boxes;
    }
}
