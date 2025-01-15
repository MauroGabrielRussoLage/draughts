package ec.com.sofka;

import ec.com.sofka.aggregate.event.GameStarted;
import ec.com.sofka.gateway.dto.GameDTO;
import ec.com.sofka.useCase.UpdateViewGameUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BusListener {
    private final UpdateViewGameUseCase updateViewGameUseCase;

    public BusListener(UpdateViewGameUseCase updateViewGameUseCase) {
        this.updateViewGameUseCase = updateViewGameUseCase;
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
        updateViewGameUseCase.apply(Mono.just(gameDTO)).subscribe();
    }

    /*@RabbitListener(queues = "${board.queue.name}")
    public void receiveBoard(BoardDTO message) {
        finalizedGameVerificationUseCase.apply(Mono.just(message)).subscribe();
    }*/
}