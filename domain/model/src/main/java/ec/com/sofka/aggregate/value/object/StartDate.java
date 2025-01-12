package ec.com.sofka.aggregate.value.object;

import ec.com.sofka.generic.interfaces.ValueObject;

import java.time.LocalDateTime;

public class StartDate implements ValueObject<LocalDateTime> {
    private final LocalDateTime value;

    public StartDate(final LocalDateTime value) {
        this.value = validate(value);
    }

    public static StartDate of(final LocalDateTime value) {
        return new StartDate(value);
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
