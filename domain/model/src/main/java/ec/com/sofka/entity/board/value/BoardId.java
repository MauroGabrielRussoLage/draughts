package ec.com.sofka.entity.board.value;

import ec.com.sofka.generic.util.Identity;

public class BoardId extends Identity {
    public BoardId() {
        super();
    }

    private BoardId(String id) {
        super(id);
    }

    public static BoardId of(String id) {
        return new BoardId(id);
    }
}
