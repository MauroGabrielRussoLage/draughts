package ec.com.sofka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:app-global.properties")
public class GlobalProperties {
    @Value("${game.exchange.name}")
    private String gameExchangeName;

    @Value("${game.queue.name}")
    private String gameQueueName;

    @Value("${board.queue.name}")
    private String boardQueueName;

    @Value("${game.routing.key}")
    private String gameRoutingKey;

    @Value("${board.routing.key}")
    private String boardRoutingKey;

    public String getGameExchangeName() {
        return gameExchangeName;
    }

    public void setGameExchangeName(String gameExchangeName) {
        this.gameExchangeName = gameExchangeName;
    }

    public String getGameQueueName() {
        return gameQueueName;
    }

    public void setGameQueueName(String gameQueueName) {
        this.gameQueueName = gameQueueName;
    }

    public String getGameRoutingKey() {
        return gameRoutingKey;
    }

    public void setGameRoutingKey(String gameRoutingKey) {
        this.gameRoutingKey = gameRoutingKey;
    }

    public String getBoardQueueName() {
        return boardQueueName;
    }

    public void setBoardQueueName(String boardQueueName) {
        this.boardQueueName = boardQueueName;
    }

    public String getBoardRoutingKey() {
        return boardRoutingKey;
    }

    public void setBoardRoutingKey(String boardRoutingKey) {
        this.boardRoutingKey = boardRoutingKey;
    }
}