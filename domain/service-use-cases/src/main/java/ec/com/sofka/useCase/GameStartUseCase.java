package ec.com.sofka.useCase;

import ec.com.sofka.aggregate.Game;
import ec.com.sofka.command.GameCommand;
import ec.com.sofka.entity.player.Player;
import ec.com.sofka.gateway.BusMessage;
import ec.com.sofka.gateway.EventStore;
import ec.com.sofka.gateway.dto.PlayerDTO;
import ec.com.sofka.gateway.repository.PlayerRepository;
import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.mapper.DTOToModelMapper;
import ec.com.sofka.response.Response;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class GameStartUseCase {
    private final EventStore repository;
    private final BusMessage busMessage;
    private final PlayerRepository playerRepository;

    public GameStartUseCase(EventStore repository, BusMessage busMessage, PlayerRepository playerRepository) {
        this.repository = repository;
        this.busMessage = busMessage;
        this.playerRepository = playerRepository;
    }

    public Mono<Response> apply(Mono<GameCommand> command) {
        return command.flatMap(gameCommand -> {
            Mono<PlayerDTO> player1DTO = playerRepository.findAll()
                    .filter(player -> player.getName().equals(gameCommand.getPlayer1Username()))
                    .singleOrEmpty()
                    .switchIfEmpty(Mono.error(new RuntimeException("Player 1 not found")));
            Mono<PlayerDTO> player2DTO = playerRepository.findAll()
                    .filter(player -> player.getName().equals(gameCommand.getPlayer2Username()))
                    .singleOrEmpty()
                    .switchIfEmpty(Mono.error(new RuntimeException("Player 2 not found")));
            return Mono.zip(player1DTO, player2DTO)
                    .flatMap(tuple -> {
                        Mono<Player> player1Mono = DTOToModelMapper.toPlayer.apply(Mono.just(tuple.getT1()));
                        Mono<Player> player2Mono = DTOToModelMapper.toPlayer.apply(Mono.just(tuple.getT2()));
                        return Mono.zip(player1Mono, player2Mono);
                    })
                    .flatMap(playersTuple -> {
                        Player player1 = playersTuple.getT1();
                        Player player2 = playersTuple.getT2();
                        if (player1.getName().equals(player2.getName())) {
                            return Mono.error(new RuntimeException("Players must not be the same"));
                        }
                        if (!player1.getName().equals(gameCommand.getLoggedUsername()) &&
                                !player2.getName().equals(gameCommand.getLoggedUsername())) {
                            return Mono.error(new RuntimeException("Logged user must be one of the players"));
                        }
                        Game game = new Game();
                        game.setPlayer1(player1);
                        game.setPlayer2(player2);
                        game.markEventsAsCommitted();
                        List<DomainEvent> uncommittedEvents = game.getUncommittedEvents();
                        return Flux.fromIterable(uncommittedEvents)
                                .flatMap(domainEvent ->
                                        repository.save(Mono.just(domainEvent))
                                                .then(busMessage.sendMsg(Mono.just(domainEvent)))
                                )
                                .then(Mono.defer(() -> Mono.just(new Response("Game created successfully"))));
                    });
        });
    }
}