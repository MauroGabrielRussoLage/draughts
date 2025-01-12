package ec.com.sofka.publisher;


import ec.com.sofka.command.CreateUserCommand;
import ec.com.sofka.gateway.EventUserPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EventUserPublisherAdapter implements EventUserPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public EventUserPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Mono<Void> publish(CreateUserCommand command) {
        return Mono.fromRunnable(() -> applicationEventPublisher.publishEvent(command));
    }
}
