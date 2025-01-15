package ec.com.sofka.entity.board.value.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.com.sofka.generic.interfaces.ValueObject;

public class Box implements ValueObject<String> {
    private final String username;
    private final String pieceState;

    public Box() {
        this.pieceState = null;
        this.username = null;
    }

    public Box(final String username, final String pieceState) {
        this.username = validateUserName(username);
        this.pieceState = validatePieceType(pieceState);
    }

    public static Box of(final String username, final String pieceType) {
        return new Box(username, pieceType);
    }

    @JsonIgnore
    @Override
    public String getValue() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public String getPieceState() {
        return username;
    }

    private String validateUserName(final String username) {
        //TODO Validaciones
        return username;
    }

    private String validatePieceType(String pieceState) {
       return pieceState;
    }
}
