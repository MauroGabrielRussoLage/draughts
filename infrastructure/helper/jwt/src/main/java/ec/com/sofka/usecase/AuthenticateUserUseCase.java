package ec.com.sofka.usecase;

import ec.com.sofka.command.LoginCommand;
import ec.com.sofka.gateway.AuthenticationService;
import ec.com.sofka.gateway.TokenProvider;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticateUserUseCase {

    private final AuthenticationService authenticationService;
    private final TokenProvider tokenProvider;

    public AuthenticateUserUseCase(AuthenticationService authenticationService, TokenProvider tokenProvider) {
        this.authenticationService = authenticationService;
        this.tokenProvider = tokenProvider;
    }

    public Mono<Map<String, String>> apply(LoginCommand command) {
        return authenticationService.authenticate(command.getUsername(), command.getPassword())
                .flatMap(userDetails -> {
                    String token = tokenProvider.generateToken(userDetails);
                    Map<String, String> responseData = new HashMap<>();
                    responseData.put("token", token);
                    return Mono.just(responseData);
                })
                .onErrorMap(e -> new AuthenticationException("Invalid username or password"));
    }
}
