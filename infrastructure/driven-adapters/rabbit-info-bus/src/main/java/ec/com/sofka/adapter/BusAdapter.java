package ec.com.sofka.adapter;

import ec.com.sofka.GlobalProperties;
import ec.com.sofka.gateway.BusMessage;
import ec.com.sofka.generic.domain.DomainEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class BusAdapter implements BusMessage {

    private final RabbitTemplate rabbitTemplate;
    private final GlobalProperties globalProperties;

    public BusAdapter(RabbitTemplate rabbitTemplate, GlobalProperties globalProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.globalProperties = globalProperties;
    }

    @Override
    public Mono<Void> sendMsg(Mono<DomainEvent> monoLog) {
        return monoLog.flatMap(event ->
                Mono.fromCallable(() -> {
                            rabbitTemplate.convertAndSend(
                                    globalProperties.getGameExchangeName(),
                                    event.getEventType().equals("PIECE_MOVED")
                                            ? globalProperties.getBoardRoutingKey()
                                            : globalProperties.getGameRoutingKey(),
                                    event
                            );
                            return event;
                        })
                        .then()
        );
    }
}