package ec.com.sofka.gateway.repository;

import ec.com.sofka.entity.log.GameEventLog;
import reactor.core.publisher.Mono;

public interface LogRepository {
    Mono<Void> createLog(Mono<GameEventLog> log);
}