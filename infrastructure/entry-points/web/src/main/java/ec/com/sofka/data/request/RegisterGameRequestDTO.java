package ec.com.sofka.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterGameRequestDTO {
    private String currentPlayerUsername;
    private String invitedPlayerUsername;
}
