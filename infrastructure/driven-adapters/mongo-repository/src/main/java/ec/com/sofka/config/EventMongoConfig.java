package ec.com.sofka.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "ec.com.sofka.eventAdapter",
        reactiveMongoTemplateRef = "eventReactiveMongoTemplate")
public class EventMongoConfig {
    @Value("${spring.data.mongodb.events-uri}")
    private String eventsUri;

    @Bean(name = "eventsReactiveDatabaseFactory")
    public ReactiveMongoDatabaseFactory eventsDatabaseFactory() {
        return new SimpleReactiveMongoDatabaseFactory(new ConnectionString(eventsUri));
    }

    @Bean(name = "eventReactiveMongoTemplate")
    public ReactiveMongoTemplate eventsMongoTemplate(@Qualifier("eventsReactiveDatabaseFactory") ReactiveMongoDatabaseFactory eventsDatabaseFactory) {
        return new ReactiveMongoTemplate(eventsDatabaseFactory);
    }
}