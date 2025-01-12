package ec.com.sofka.gateway;

import ec.com.sofka.command.CreateUserCommand;
import reactor.core.publisher.Mono;

public interface EventUserPublisher {
    Mono<Void> publish(CreateUserCommand command);
}
