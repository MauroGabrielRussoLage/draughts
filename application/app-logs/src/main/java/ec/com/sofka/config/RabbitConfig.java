package ec.com.sofka.config;

import ec.com.sofka.GlobalProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final GlobalProperties globalProperties;

    public RabbitConfig(GlobalProperties globalProperties) {
        this.globalProperties = globalProperties;
    }

    @Bean
    public TopicExchange gameExchange() {
        return new TopicExchange(globalProperties.getGameExchangeName());
    }

    @Bean
    public Queue gameQueue() {
        return new Queue(globalProperties.getGameQueueName(), true);
    }

    @Bean
    public Queue boardQueue() {
        return new Queue(globalProperties.getBoardQueueName(), true);
    }

    @Bean
    public Binding gameBinding(Queue gameQueue, TopicExchange gameExchange) {
        return BindingBuilder.bind(gameQueue).to(gameExchange)
                .with(globalProperties.getGameRoutingKey());
    }

    @Bean
    public Binding boardBinding(Queue boardQueue, TopicExchange gameExchange) {
        return BindingBuilder.bind(boardQueue).to(gameExchange)
                .with(globalProperties.getBoardRoutingKey());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
