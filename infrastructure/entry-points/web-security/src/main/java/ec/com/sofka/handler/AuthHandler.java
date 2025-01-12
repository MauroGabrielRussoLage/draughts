package ec.com.sofka.handler;

import ec.com.sofka.JwtUtil;
import ec.com.sofka.command.CreateUserCommand;
import ec.com.sofka.data.AuthRequestDTO;
import ec.com.sofka.data.RegisterRequestDTO;
import ec.com.sofka.usecase.CreateUserUseCase;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final JwtUtil jwtUtil;
    private final CreateUserUseCase createUserUseCase;

    public AuthHandler(ReactiveAuthenticationManager authenticationManager, JwtUtil jwtUtil, CreateUserUseCase createUserUseCase) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.createUserUseCase = createUserUseCase;
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<AuthRequestDTO>() {
                })
                .flatMap(authRequest -> authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authRequest.getUsername(),
                                authRequest.getPassword()
                        )
                ).flatMap(authentication -> {
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String token = jwtUtil.generateToken(userDetails);

                    Map<String, String> responseData = new HashMap<>();
                    responseData.put("token", token);

                    return ServerResponse.ok().bodyValue(responseData);
                }))
                .onErrorResume(Exception.class, e -> HandleError.handle(request, "BAD_CREDENTIALS", HttpStatus.UNAUTHORIZED, "Authentication failed. Invalid username or password."));
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<RegisterRequestDTO>() {
                })
                .flatMap(registerRequest -> {
                    CreateUserCommand command = new CreateUserCommand(
                            registerRequest.getName(),
                            registerRequest.getLastname(),
                            registerRequest.getUsername(),
                            registerRequest.getPassword(),
                            registerRequest.getRoles()
                    );
                    return createUserUseCase.apply(command)
                            .flatMap(userCreated -> authenticationManager.authenticate(
                                                    new UsernamePasswordAuthenticationToken(
                                                            command.getUsername(),
                                                            command.getPassword(),
                                                            command.getRoles().stream()
                                                                    .map(SimpleGrantedAuthority::new)
                                                                    .collect(Collectors.toList())
                                                    )
                                            )
                                            .flatMap(authentication -> {
                                                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                                                String token = jwtUtil.generateToken(userDetails);
                                                Map<String, String> responseData = new HashMap<>();
                                                responseData.put("token", token);
                                                return ServerResponse.status(HttpStatus.CREATED).bodyValue(responseData);
                                            })
                            );
                })
                .onErrorResume(Exception.class, e -> HandleError.handle(request, "UNKNOWN_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}