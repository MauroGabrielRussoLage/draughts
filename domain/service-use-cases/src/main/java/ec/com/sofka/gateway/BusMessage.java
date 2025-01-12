package ec.com.sofka.gateway;

import ec.com.sofka.entity.log.GameEventLog;
import ec.com.sofka.generic.domain.DomainEvent;
import reactor.core.publisher.Mono;

public interface BusMessage {
    Mono<Void> sendMsg(Mono<DomainEvent> message);
}