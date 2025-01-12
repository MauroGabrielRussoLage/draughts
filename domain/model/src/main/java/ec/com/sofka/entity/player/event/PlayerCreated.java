package ec.com.sofka.entity.player.event;

import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.util.EventEnum;

public class PlayerCreated extends DomainEvent {
    private String name;
    private String pieceColor;

    public PlayerCreated(String name, String pieceColor) {
        super(EventEnum.PLAYER_CREATED.name());
        this.name = name;
        this.pieceColor = pieceColor;
    }

    public PlayerCreated() {
        super(EventEnum.PLAYER_CREATED.name());
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
}
