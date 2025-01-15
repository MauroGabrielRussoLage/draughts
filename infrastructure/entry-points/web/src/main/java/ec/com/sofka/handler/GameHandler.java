package ec.com.sofka.handler;

import ec.com.sofka.aggregate.value.GameId;
import ec.com.sofka.command.GameCommand;
import ec.com.sofka.command.MovementCommand;
import ec.com.sofka.data.MovementRequestDTO;
import ec.com.sofka.data.RegisterGameRequestDTO;
import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.gateway.repository.GameCommandSideRepository;
import ec.com.sofka.response.Response;
import ec.com.sofka.useCase.GameStartUseCase;
import ec.com.sofka.useCase.MovementUseCase;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GameHandler {
    private final GameStartUseCase gameStartUseCase;
    private final MovementUseCase movementUseCase;
    private final UserRepository userRepository;
    private final GameCommandSideRepository gameCommandSideRepository;

    public GameHandler(GameStartUseCase gameStartUseCase, MovementUseCase movementUseCase, UserRepository userRepository, GameCommandSideRepository gameCommandSideRepository) {
        this.gameStartUseCase = gameStartUseCase;
        this.movementUseCase = movementUseCase;
        this.userRepository = userRepository;
        this.gameCommandSideRepository = gameCommandSideRepository;
    }

    public Mono<ServerResponse> startGame(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<RegisterGameRequestDTO>() {})
                .flatMap(registerGameRequestDTO ->
                        ReactiveSecurityContextHolder.getContext()
                                .map(SecurityContext::getAuthentication)
                                .flatMap(authentication ->
                                        userRepository.findByUsername(registerGameRequestDTO.getInvitedPlayerUsername())
                                                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invited player does not exist")))
                                                .flatMap(invitedUser ->
                                                        gameCommandSideRepository.existsGameWithPlayerAndNotFinalized(authentication.getName())
                                                                .flatMap(existsForPlayer1 ->
                                                                        gameCommandSideRepository.existsGameWithPlayerAndNotFinalized(registerGameRequestDTO.getInvitedPlayerUsername())
                                                                                .map(existsForPlayer2 -> existsForPlayer1 || existsForPlayer2)
                                                                )
                                                                .flatMap(gameExists -> {
                                                                    if (gameExists) {
                                                                        return Mono.error(new IllegalArgumentException("An active game already exists for one of the players"));
                                                                    }
                                                                    GameCommand command = new GameCommand.Builder()
                                                                            .withPlayer1Id(authentication.getName())
                                                                            .withPlayer2Id(registerGameRequestDTO.getInvitedPlayerUsername())
                                                                            .build();
                                                                    return gameStartUseCase.apply(Mono.just(command))
                                                                            .flatMap(dto -> ServerResponse.ok().bodyValue(dto));
                                                                })
                                                )
                                )
                ).onErrorResume(IllegalArgumentException.class, ex ->
                        ServerResponse.badRequest().bodyValue(new Response("Illegal argument: " + ex.getMessage()))
                ).onErrorResume(RuntimeException.class, ex ->
                        ServerResponse.status(500).bodyValue(new Response("Runtime Exception: " + ex.getMessage()))
                ).onErrorResume(Exception.class, ex ->
                        ServerResponse.status(500).bodyValue(new Response("Unexpected server error occurred: " + ex.getMessage()))
                );
    }

    public Mono<ServerResponse> makeMovement(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<MovementRequestDTO>() {})
                .flatMap(movementRequestDTO ->
                        ReactiveSecurityContextHolder.getContext()
                                .map(SecurityContext::getAuthentication)
                                .flatMap(authentication ->
                                        gameCommandSideRepository.findById(Mono.just(GameId.of(movementRequestDTO.getGameId())))
                                                .switchIfEmpty(Mono.error(new IllegalArgumentException("Indicated game does not exist")))
                                                .flatMap(gameDTO -> {
                                                    if ("Game finalized".equals(gameDTO.getStatus())) {
                                                        return Mono.error(new IllegalArgumentException("The game is already finalized"));
                                                    }
                                                    MovementCommand command = new MovementCommand.Builder()
                                                            .withAggregateId(movementRequestDTO.getGameId())
                                                            .withPlayerId(authentication.getName())
                                                            .withDestination(movementRequestDTO.getDestinationPosition())
                                                            .withSource(movementRequestDTO.getOriginPosition())
                                                            .build();
                                                    return movementUseCase.apply(Mono.just(command))
                                                            .flatMap(dto -> ServerResponse.ok().bodyValue(dto));
                                                })
                                )
                )
                .onErrorResume(IllegalArgumentException.class, ex ->
                        ServerResponse.badRequest().bodyValue(new Response("Illegal argument: " + ex.getMessage()))
                )
                .onErrorResume(RuntimeException.class, ex ->
                        ServerResponse.status(500).bodyValue(new Response("Runtime Exception: " + ex.getMessage()))
                )
                .onErrorResume(Exception.class, ex ->
                        ServerResponse.status(500).bodyValue(new Response("Unexpected server error occurred: " + ex.getMessage()))
                );
    }
}
