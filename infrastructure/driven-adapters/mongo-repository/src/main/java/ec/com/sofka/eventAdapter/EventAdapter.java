package ec.com.sofka.eventAdapter;

import ec.com.sofka.JSONMap;
import ec.com.sofka.data.EventDocument;
import ec.com.sofka.gateway.EventStore;
import ec.com.sofka.generic.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Repository
public class EventAdapter implements EventStore {

    private final JSONMap mapper;
    private final ReactiveMongoTemplate eventMongoTemplate;

    public EventAdapter(JSONMap mapper, @Qualifier("eventReactiveMongoTemplate") ReactiveMongoTemplate eventMongoTemplate) {
        this.mapper = mapper;
        this.eventMongoTemplate = eventMongoTemplate;
    }

    @Override
    public Mono<DomainEvent> save(Mono<DomainEvent> eventMono) {
        return eventMono.flatMap(event -> {
            EventDocument e = new EventDocument(
                    event.getEventId(),
                    event.getAggregateRootId(),
                    event.getEventType(),
                    EventDocument.wrapEvent(event, mapper),
                    event.getWhen().toString(),
                    event.getVersion()
            );
            return eventMongoTemplate.save(e).thenReturn(event);
        });
    }

    @Override
    public Flux<DomainEvent> findAggregate(Mono<String> aggregateId) {
        return aggregateId.flatMapMany(id ->
                eventMongoTemplate.find(
                                Query.query(Criteria.where("aggregateId").is(id)),
                                EventDocument.class
                        )
                        .map(doc -> doc.deserializeEvent(mapper))
                        .sort(Comparator.comparing(DomainEvent::getVersion))
        );
    }

    @Override
    public Flux<DomainEvent> findAllAggregates() {
        return eventMongoTemplate
                .findAll(EventDocument.class)
                .map(eventDoc -> eventDoc.deserializeEvent(mapper))
                .sort(Comparator.comparing(DomainEvent::getAggregateRootId)
                        .thenComparing(DomainEvent::getVersion, Comparator.reverseOrder()));
    }
}
