package ec.com.sofka.data;

import ec.com.sofka.JSONMap;
import ec.com.sofka.generic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Event")
public class EventDocument {
    @Id
    private String id;
    private String aggregateId;
    private String eventType;
    private String eventData;
    private String timestamp;
    private Long version;

    public static String wrapEvent(DomainEvent domainEvent, JSONMap eventSerializer) {
        return eventSerializer.writeToJson(domainEvent);
    }

    public DomainEvent deserializeEvent(JSONMap eventSerializer) {
        try {
            String className = Arrays.stream(this.getEventType().toLowerCase().split("_"))
                    .map(part -> Character.toUpperCase(part.charAt(0)) + part.substring(1))
                    .collect(Collectors.joining());

            return (DomainEvent) eventSerializer
                    .readFromJson(this.getEventData(), Class.forName("ec.com.sofka.aggregate.events." + className));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
