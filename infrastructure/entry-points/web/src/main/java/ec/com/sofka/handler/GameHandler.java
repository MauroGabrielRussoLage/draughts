package ec.com.sofka.handler;

import ec.com.sofka.command.GameCommand;
import ec.com.sofka.data.request.RegisterGameRequestDTO;
import ec.com.sofka.useCase.GameStartUseCase;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GameHandler {
    private final GameStartUseCase gameStartUseCase;

    public GameHandler(GameStartUseCase gameStartUseCase) {
        this.gameStartUseCase = gameStartUseCase;
    }

    public Mono<ServerResponse> startGame(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<RegisterGameRequestDTO>() {})
                .flatMap(registerGameRequestDTO -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    GameCommand command = new GameCommand.Builder()
                            .withPlayer1Id(registerGameRequestDTO.getCurrentPlayerUsername())
                            .withPlayer2Id(registerGameRequestDTO.getInvitedPlayerUsername())
                            .withLoggedUsername(authentication.getName())
                            .build();
                    return gameStartUseCase.apply(Mono.just(command)).flatMap(dto -> ServerResponse.ok().bodyValue(dto));
        });
    }
}
