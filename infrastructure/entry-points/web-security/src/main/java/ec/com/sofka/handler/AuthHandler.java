package ec.com.sofka.handler;

import ec.com.sofka.command.CreateUserCommand;
import ec.com.sofka.command.LoginCommand;
import ec.com.sofka.data.AuthRequestDTO;
import ec.com.sofka.data.RegisterRequestDTO;
import ec.com.sofka.gateway.TokenProvider;
import ec.com.sofka.response.Response;
import ec.com.sofka.usecase.AuthenticateUserUseCase;
import ec.com.sofka.usecase.CreateUserUseCase;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthHandler {

    private final ReactiveAuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final CreateUserUseCase createUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final PasswordEncoder passwordEncoder;

    public AuthHandler(ReactiveAuthenticationManager authenticationManager, TokenProvider tokenProvider, CreateUserUseCase createUserUseCase, AuthenticateUserUseCase authenticateUserUseCase, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.createUserUseCase = createUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<AuthRequestDTO>() {
                })
                .flatMap(authRequest -> {
                    LoginCommand command = new LoginCommand(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    );
                    return authenticateUserUseCase.apply(command)
                            .flatMap(authResponse -> ServerResponse.ok().bodyValue(authResponse))
                            .onErrorResume(AuthenticationException.class, e -> HandleError.handle(
                                    request,
                                    "BAD_CREDENTIALS",
                                    HttpStatus.UNAUTHORIZED,
                                    e.getMessage()
                            ));
                }).onErrorResume(IllegalArgumentException.class, ex ->
                        ServerResponse.badRequest().bodyValue(new Response("Illegal argument: " + ex.getMessage()))
                ).onErrorResume(RuntimeException.class, ex ->
                        ServerResponse.status(500).bodyValue(new Response("Runtime Exception: " + ex.getMessage()))
                ).onErrorResume(Exception.class, ex ->
                        ServerResponse.status(500).bodyValue(new Response("Unexpected server error occurred: " + ex.getMessage()))
                );
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<RegisterRequestDTO>() {
                })
                .flatMap(registerRequest -> {
                    CreateUserCommand command = new CreateUserCommand(
                            registerRequest.getName(),
                            registerRequest.getLastname(),
                            registerRequest.getUsername(),
                            passwordEncoder.encode(registerRequest.getPassword()),
                            registerRequest.getRoles()
                    );
                    return createUserUseCase.apply(command)
                                .flatMap(userCreated -> authenticationManager.authenticate(
                                                        new UsernamePasswordAuthenticationToken(
                                                                registerRequest.getUsername(),
                                                                registerRequest.getPassword()
                                                        )
                                                )
                                            .flatMap(authentication -> {
                                                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                                                String token = tokenProvider.generateToken(userDetails);
                                                Map<String, String> responseData = new HashMap<>();
                                                responseData.put("token", token);
                                                return ServerResponse.status(HttpStatus.CREATED).bodyValue(responseData);
                                            })
                            );
                })
                .onErrorResume(Exception.class, e -> HandleError.handle(request, "UNKNOWN_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}