package ec.com.sofka.aggregate.value.object;

import ec.com.sofka.generic.interfaces.ValueObject;

import java.time.LocalDateTime;

public class EndDate implements ValueObject<LocalDateTime> {
    private final LocalDateTime value;

    public EndDate(final LocalDateTime value) {
        this.value = validate(value);
    }

    public static EndDate of(final LocalDateTime value) {
        return new EndDate(value);
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
