package ec.com.sofka.entity.board.value.object;

import ec.com.sofka.generic.interfaces.ValueObject;

public class Box implements ValueObject<String> {
    private final String value;

    public Box() {
        this.value = null;
    }

    public Box(final String value) {
        this.value = validate(value);
    }

    public static Box of(final String value) {
        return new Box(value);
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
