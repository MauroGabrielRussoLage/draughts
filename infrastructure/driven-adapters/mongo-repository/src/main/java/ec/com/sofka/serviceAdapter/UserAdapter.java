package ec.com.sofka.serviceAdapter;

import ec.com.sofka.gateway.UserRepository;
import ec.com.sofka.model.User;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserAdapter implements UserRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public UserAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<User> save(User user) {
        return reactiveMongoTemplate.save(user);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        Query query = Query.query(Criteria.where("username").is(username));
        return reactiveMongoTemplate.findOne(query, User.class);
    }

    @Override
    public Mono<Boolean> existsByUsername(String username) {
        Query query = Query.query(Criteria.where("username").is(username));
        return reactiveMongoTemplate.exists(query, User.class);
    }
}
