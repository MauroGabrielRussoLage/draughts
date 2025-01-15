package ec.com.sofka.useCase;

import ec.com.sofka.aggregate.Game;
import ec.com.sofka.aggregate.value.object.CurrentTurn;
import ec.com.sofka.aggregate.value.object.StartDate;
import ec.com.sofka.aggregate.value.object.Status;
import ec.com.sofka.command.BoardGenerationCommand;
import ec.com.sofka.command.GameCommand;
import ec.com.sofka.entity.player.Player;
import ec.com.sofka.entity.player.value.object.Name;
import ec.com.sofka.entity.player.value.object.PieceColor;
import ec.com.sofka.gateway.BusMessage;
import ec.com.sofka.gateway.EventStore;
import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.response.Response;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class GameStartUseCase {
    private final EventStore repository;
    private final BusMessage busMessage;
    private final List<String> pieceColors = new ArrayList<>(List.of("WHITE", "BLACK"));
    private final BoardGenerationUseCase boardGenerationUseCase;

    public GameStartUseCase(EventStore repository, BusMessage busMessage, BoardGenerationUseCase boardGenerationUseCase) {
        this.repository = repository;
        this.busMessage = busMessage;
        this.boardGenerationUseCase = boardGenerationUseCase;
    }

    public Mono<Response> apply(Mono<GameCommand> command) {
        return command.flatMap(gameCommand -> {
            if (gameCommand.getPlayer1Username().equals(gameCommand.getPlayer2Username())) {
                return Mono.error(new IllegalArgumentException("Players must not be the same"));
            }
            Random random = new Random();
            Collections.shuffle(pieceColors, random);
            Player player1 = new Player(Name.of(gameCommand.getPlayer1Username()), PieceColor.of(pieceColors.getFirst()));
            Player player2 = new Player(Name.of(gameCommand.getPlayer2Username()), PieceColor.of(pieceColors.getLast()));
            return boardGenerationUseCase.apply(Mono.just(new BoardGenerationCommand(
                    null,
                    player1.getPieceColor().getValue(),
                    gameCommand.getPlayer1Username(),
                    player2.getPieceColor().getValue(),
                    gameCommand.getPlayer2Username()
            ))).flatMap(board -> {
                Game game = new Game(
                        player1,
                        player2,
                        board,
                        Status.of("Game started"),
                        CurrentTurn.of(
                                player1.getPieceColor().getValue().equalsIgnoreCase("WHITE") ?
                                        player1.getName().getValue() :
                                        player2.getName().getValue()),
                        StartDate.of(LocalDateTime.now())
                );
                List<DomainEvent> uncommittedEvents = game.getUncommittedEvents();
                game.markEventsAsCommitted();
                return Flux.fromIterable(uncommittedEvents)
                        .flatMap(domainEvent ->
                                repository.save(Mono.just(domainEvent))
                                        .then(busMessage.sendMsg(Mono.just(domainEvent)))
                        )
                        .then(Mono.defer(() -> Mono.just(new Response("Game created successfully:" +
                                " " + game.getId().getValue() +
                                " " + game.getPlayer1().getName().getValue() +
                                " " + game.getPlayer1().getPieceColor().getValue() +
                                " " + game.getPlayer2().getName().getValue() +
                                " " + game.getPlayer2().getPieceColor().getValue()
                        ))));
            });
        });
    }
}