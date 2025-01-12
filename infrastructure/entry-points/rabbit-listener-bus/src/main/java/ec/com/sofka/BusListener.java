package ec.com.sofka;

import ec.com.sofka.entity.log.GameEventLog;
import ec.com.sofka.useCase.PrintLogUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

// Agregar implementación de interfaz
@Service
public class BusListener {
    private final PrintLogUseCase printLogUseCase;

    public BusListener(PrintLogUseCase printLogUseCase) {
        this.printLogUseCase = printLogUseCase;
    }

    @RabbitListener(queues = "${example.queue.name}")
    public void receiveMessage(GameEventLog message) {
        printLogUseCase.apply(Mono.just(message)).subscribe();
    }
}