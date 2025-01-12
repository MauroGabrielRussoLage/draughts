package ec.com.sofka.entity.movement.value.object;

import ec.com.sofka.generic.interfaces.ValueObject;

public class Coordinate implements ValueObject<String> {
    private final String value;

    public Coordinate() {
        this.value = null;
    }

    public Coordinate(final String value) {
        this.value = validate(value);
    }

    public static Coordinate of(final String value) {
        return new Coordinate(value);
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
