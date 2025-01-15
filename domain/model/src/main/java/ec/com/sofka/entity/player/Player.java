package ec.com.sofka.entity.player;

import ec.com.sofka.entity.player.value.PlayerId;
import ec.com.sofka.entity.player.value.object.Name;
import ec.com.sofka.entity.player.value.object.PieceColor;
import ec.com.sofka.generic.util.Entity;
import org.springframework.data.annotation.PersistenceConstructor;

public class Player extends Entity<PlayerId> {
    private Name name;
    private PieceColor pieceColor;

    public Player(Name name, PieceColor pieceColor) {
        super(new PlayerId());
        this.name = name;
        this.pieceColor = pieceColor;
    }

    @PersistenceConstructor
    public Player(PlayerId id, Name name, PieceColor pieceColor) {
        super(id);
        this.name = name;
        this.pieceColor = pieceColor;
    }

    public Player() {
        super(null);
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }
}
