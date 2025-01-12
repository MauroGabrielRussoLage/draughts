package ec.com.sofka.entity.player.value.object;

import ec.com.sofka.generic.interfaces.ValueObject;

public class Name implements ValueObject<String> {
    private final String value;

    public Name() {
        this.value = null;
    }

    public Name(final String value) {
        this.value = validate(value);
    }

    public static Name of(final String value) {
        return new Name(value);
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
