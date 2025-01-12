package ec.com.sofka.config;

import ec.com.sofka.GlobalProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
    public TopicExchange exampleExchange() {
        return new TopicExchange(globalProperties.getExampleExchangeName());
    }

    @Bean
    public Queue exampleQueue() {
        return new Queue(globalProperties.getExampleQueueName(), true);
    }

    @Bean
    public Binding exampleBinding(Queue branchTransferQueue, TopicExchange transferExchange) {
        return BindingBuilder.bind(branchTransferQueue).to(transferExchange)
                .with(globalProperties.getExampleRoutingKey());
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
