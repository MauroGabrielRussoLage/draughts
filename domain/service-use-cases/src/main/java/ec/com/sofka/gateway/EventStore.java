package ec.com.sofka.gateway;

import ec.com.sofka.generic.domain.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventStore {
    Mono<DomainEvent> save(Mono<DomainEvent> event);

    Flux<DomainEvent> findAggregate(Mono<String> aggregateId);

    Flux<DomainEvent> findAllAggregates();
}
