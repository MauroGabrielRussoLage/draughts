package ec.com.sofka.generic.util;

public abstract class Query {
    private final String aggregateId;

    protected Query(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }
}