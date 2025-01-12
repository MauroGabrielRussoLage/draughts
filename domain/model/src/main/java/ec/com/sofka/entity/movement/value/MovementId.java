package ec.com.sofka.entity.movement.value;

import ec.com.sofka.generic.util.Identity;

public class MovementId extends Identity {
    public MovementId() {
        super();
    }

    private MovementId(String id) {
        super(id);
    }

    public static MovementId of(String id) {
        return new MovementId(id);
    }
}
