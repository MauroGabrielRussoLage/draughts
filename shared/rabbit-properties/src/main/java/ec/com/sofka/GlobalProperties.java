package ec.com.sofka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:app-global.properties")
public class GlobalProperties {
    @Value("${example.exchange.name}")
    private String exampleExchangeName;

    @Value("${example.queue.name}")
    private String exampleQueueName;

    @Value("${example.routing.key}")
    private String exampleRoutingKey;

    public String getExampleExchangeName() {
        return exampleExchangeName;
    }

    public void setExampleExchangeName(String exampleExchangeName) {
        this.exampleExchangeName = exampleExchangeName;
    }

    public String getExampleQueueName() {
        return exampleQueueName;
    }

    public void setExampleQueueName(String exampleQueueName) {
        this.exampleQueueName = exampleQueueName;
    }

    public String getExampleRoutingKey() {
        return exampleRoutingKey;
    }

    public void setExampleRoutingKey(String exampleRoutingKey) {
        this.exampleRoutingKey = exampleRoutingKey;
    }
}