package ec.com.sofka.aggregate.value;

import ec.com.sofka.generic.util.Identity;

public class GameId extends Identity {
    public GameId() {
        super();
    }

    public GameId(final String id) {
        super(id);
    }

    public static GameId of(final String id) {
        return new GameId(id);
    }
}