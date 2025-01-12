package ec.com.sofka.gateway;

import ec.com.sofka.entity.log.GameEventLog;
import reactor.core.publisher.Mono;

public interface BusMessageListener {
    void receiveMsg(Mono<GameEventLog> log);
}