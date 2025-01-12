package ec.com.sofka.generic.util;

public abstract class Command {
    private final String aggregateId;

    protected Command(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }
}