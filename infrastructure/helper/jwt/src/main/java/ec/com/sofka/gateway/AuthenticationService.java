package ec.com.sofka.gateway;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<UserDetails> authenticate(String username, String password);
}
