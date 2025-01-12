package ec.com.sofka.entity.player.value.object;

import ec.com.sofka.generic.interfaces.ValueObject;

public class PieceColor implements ValueObject<String> {
    private final String value;

    public PieceColor() {
        this.value = null;
    }

    public PieceColor(final String value) {
        this.value = validate(value);
    }

    public static PieceColor of(final String value) {
        return new PieceColor(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    private String validate(final String value) {
        //TODO Validaciones
        return value;
    }
}
