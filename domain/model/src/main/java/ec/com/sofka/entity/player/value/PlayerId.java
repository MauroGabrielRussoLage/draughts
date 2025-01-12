package ec.com.sofka.entity.player.value;

import ec.com.sofka.generic.util.Identity;

public class PlayerId extends Identity {
    public PlayerId() {
        super();
    }

    private PlayerId(String id) {
        super(id);
    }

    public static PlayerId of(String id) {
        return new PlayerId(id);
    }
}
