package ec.com.sofka.usecase;

import ec.com.sofka.command.CreateUserCommand;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.model.User;
import reactor.core.publisher.Mono;

public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> apply(CreateUserCommand command) {
        return userRepository
                .existsByUsername(command.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("The username is already in use."));
                    }
                    User user = command.toUser();
                    user.setPassword(user.getPassword());
                    return userRepository.save(user);
                });
    }
}
