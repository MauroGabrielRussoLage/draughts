package ec.com.sofka.generic.util;

import ec.com.sofka.generic.domain.DomainActionsContainer;
import ec.com.sofka.generic.domain.DomainActionsHandler;
import ec.com.sofka.generic.domain.DomainEvent;
import ec.com.sofka.generic.interfaces.Event;

import java.util.List;

public abstract class AggregateRoot<I extends Identity> extends Entity<I> {
    private final DomainActionsHandler actionsHandler = new DomainActionsHandler();

    protected AggregateRoot(final I id) {
        super(id);
    }

    public List<DomainEvent> getUncommittedEvents() {
        return List.copyOf(actionsHandler.getEvents());
    }

    public void markEventsAsCommitted() {
        actionsHandler.getEvents().clear();
    }

    protected void setSubscription(final DomainActionsContainer container) {
        actionsHandler.subscribe(container);
    }

    protected Event addEvent(final DomainEvent event) {
        final String aggregateName = this.getId()
                .getClass()
                .getSimpleName()
                .replace("Id", "")
                .toLowerCase();
        event.setAggregateRootId(getId().getValue());
        event.setAggregateRootName(aggregateName);

        return actionsHandler.append(event);
    }
}