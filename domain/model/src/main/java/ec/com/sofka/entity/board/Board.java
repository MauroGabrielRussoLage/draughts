package ec.com.sofka.entity.board;

import ec.com.sofka.entity.board.value.BoardId;
import ec.com.sofka.entity.board.value.object.Box;
import ec.com.sofka.generic.util.Entity;

public class Board extends Entity<BoardId> {
    private Box[][] boxes; //8x8

    public Board(Box[][] boxes) {
        super(new BoardId());
        this.boxes = boxes;
    }

    public Board() {
        super(null);
    }

    public Box[][] getBoxes() {
        return boxes;
    }

    public void setBoxes(Box[][] boxes) {
        this.boxes = boxes;
    }
}
