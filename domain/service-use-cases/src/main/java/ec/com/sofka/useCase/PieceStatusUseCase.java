package ec.com.sofka.useCase;

import ec.com.sofka.command.MovementCommand;
import reactor.core.publisher.Mono;

public class PieceStatusUseCase {

    public Mono<Object> apply(Mono<MovementCommand> command) {
        return null;
    }
}
