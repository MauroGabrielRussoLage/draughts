package ec.com.sofka.entity.movement.value.object;

import ec.com.sofka.generic.interfaces.ValueObject;

public class CapturedPieces implements ValueObject<Boolean> {
    private final Boolean value;

    public CapturedPieces(final Boolean value) {
        this.value = validate(value);
    }

    public static CapturedPieces of(final Boolean value) {
        return new CapturedPieces(value);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    private Boolean validate(final Boolean value) {
        //TODO Validaciones
        if (value == null) {
            throw new IllegalArgumentException("The status can't be null");
        }
        return value;
    }
}
