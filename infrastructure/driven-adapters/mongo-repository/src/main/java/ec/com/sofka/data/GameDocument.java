package ec.com.sofka.data;

import ec.com.sofka.aggregate.value.object.*;
import ec.com.sofka.entity.board.Board;
import ec.com.sofka.entity.movement.Movement;
import ec.com.sofka.entity.player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Game")
public class GameDocument {
    @Id
    private String id;
    protected Player player1;
    protected Player player2;
    protected Board board;
    protected String status;
    protected String currentTurn;
    protected String winner;
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected List<Movement> movements;
}
