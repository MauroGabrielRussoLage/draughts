package ec.com.sofka.gateway;

import ec.com.sofka.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(User user);

    Mono<User> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);
}
