package ec.com.sofka;

import ec.com.sofka.aggregate.event.GameStarted;
import ec.com.sofka.aggregate.event.PieceMoved;
import ec.com.sofka.gateway.querySideDTO.GameDTO;
import ec.com.sofka.gateway.querySideDTO.MovementDTO;
import ec.com.sofka.useCase.FinalizedGameVerificationUseCase;
import ec.com.sofka.useCase.CreateViewGameUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BusListener {
    private final CreateViewGameUseCase createViewGameUseCase;
    private final FinalizedGameVerificationUseCase finalizedGameVerificationUseCase;

    public BusListener(CreateViewGameUseCase createViewGameUseCase, FinalizedGameVerificationUseCase finalizedGameVerificationUseCase) {
        this.createViewGameUseCase = createViewGameUseCase;
        this.finalizedGameVerificationUseCase = finalizedGameVerificationUseCase;
    }

    @RabbitListener(queues = "${game.queue.name}")
    public void receiveGame(GameStarted message) {
        GameDTO gameDTO = new GameDTO(
                message.getBoard(),
                message.getCurrentTurn(),
                message.getAggregateRootId(),
                message.getPlayer1Color(),
                message.getPlayer1Name(),
                message.getPlayer2Color(),
                message.getPlayer2Name(),
                message.getStartDate(),
                message.getStatus()
        );
        createViewGameUseCase.apply(Mono.just(gameDTO)).subscribe();
    }

    @RabbitListener(queues = "${board.queue.name}")
    public void receiveBoard(PieceMoved message) {
        MovementDTO movementDTO = new MovementDTO(
                message.getBoard(),
                message.getCapturedPieces(),
                message.getDestination(),
                message.getGameId(),
                message.getMovementDate(),
                message.getPlayerName(),
                message.getSource()
        );
        finalizedGameVerificationUseCase.apply(Mono.just(movementDTO)).subscribe();
    }
}