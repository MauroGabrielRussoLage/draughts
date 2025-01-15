package ec.com.sofka.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovementRequestDTO {
    private String gameId;
    private String originPosition;
    private String destinationPosition;
}
