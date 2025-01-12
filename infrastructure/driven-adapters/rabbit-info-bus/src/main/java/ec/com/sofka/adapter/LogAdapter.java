package ec.com.sofka.adapter;


import ec.com.sofka.data.LogDocument;
import ec.com.sofka.entity.log.GameEventLog;
import ec.com.sofka.gateway.repository.LogRepository;
import ec.com.sofka.mapper.ModelToDocumentMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LogAdapter implements LogRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public LogAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Void> createLog(Mono<GameEventLog> logMono) {
        //TODO Arreglar logica
        return reactiveMongoTemplate.save(logMono).then();
        /*return logMono.flatMap(log -> {
            Query query = new Query(Criteria.where("_id").is(log.getTransactionId()));
            return ModelToDocumentMapper.toLog.apply(Mono.just(log))
                    .flatMap(logDocument -> {
                        Update update = new Update().push("logs", logDocument);
                        return reactiveMongoTemplate.upsert(query, update, LogDocument.class)
                                .flatMap(result -> result.getModifiedCount() > 0
                                        ? Mono.just(log)
                                        : Mono.error(new RuntimeException("Failed to create log")));
                    });
        }).then();*/
    }
}