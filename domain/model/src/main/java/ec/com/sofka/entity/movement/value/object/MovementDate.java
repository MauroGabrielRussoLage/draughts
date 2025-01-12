package ec.com.sofka.entity.movement.value.object;

import ec.com.sofka.generic.interfaces.ValueObject;

import java.time.LocalDateTime;

public class MovementDate implements ValueObject<LocalDateTime> {
    private final LocalDateTime value;

    public MovementDate(final LocalDateTime value) {
        this.value = validate(value);
    }

    public static MovementDate of(final LocalDateTime value) {
        return new MovementDate(value);
    }

    @Override
    public LocalDateTime getValue() {
        return value;
    }

    private LocalDateTime validate(final LocalDateTime value) {
        //TODO Validaciones
        if (value == null) {
            throw new IllegalArgumentException("The status can't be null");
        }
        return value;
    }
}
