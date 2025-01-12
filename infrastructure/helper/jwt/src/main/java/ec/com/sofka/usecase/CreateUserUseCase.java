package ec.com.sofka.usecase;

import ec.com.sofka.command.CreateUserCommand;
import ec.com.sofka.gateway.EventUserPublisher;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final EventUserPublisher eventUserPublisher;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, EventUserPublisher eventUserPublisher, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventUserPublisher = eventUserPublisher;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<User> apply(CreateUserCommand command) {
        return userRepository
                .existsByUsername(command.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("The username is already in use."));
                    }
                    User user = command.toUser();
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    return userRepository.save(user);
                })
                .flatMap(user ->
                        eventUserPublisher.publish(command)
                                .thenReturn(user)
                );
    }
}
