package ec.com.sofka.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "ec.com.sofka.serviceAdapter",
        reactiveMongoTemplateRef = "gameReactiveMongoTemplate")
public class GameMongoConfig {
    @Value("${spring.data.mongodb.game-uri}")
    private String accountsUri;

    @Primary
    @Bean(name = "gameReactiveDatabaseFactory")
    public ReactiveMongoDatabaseFactory gameDatabaseFactory() {
        return new SimpleReactiveMongoDatabaseFactory(new ConnectionString(accountsUri));
    }

    @Primary
    @Bean(name = "gameReactiveMongoTemplate")
    public ReactiveMongoTemplate gameMongoTemplate(@Qualifier("gameReactiveDatabaseFactory") ReactiveMongoDatabaseFactory accountsDatabaseFactory) {
        return new ReactiveMongoTemplate(accountsDatabaseFactory);
    }
}