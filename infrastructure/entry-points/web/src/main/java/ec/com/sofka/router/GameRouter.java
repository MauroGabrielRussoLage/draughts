package ec.com.sofka.router;

import ec.com.sofka.data.MovementRequestDTO;
import ec.com.sofka.data.RegisterGameRequestDTO;
import ec.com.sofka.handler.GameHandler;
import ec.com.sofka.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Tag(name = "Draughts API", description = "Endpoints for draughts game")
@Configuration
public class GameRouter {

    private final GameHandler gameHandler;

    public GameRouter(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/game/start",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"game"},
                            operationId = "startGame",
                            summary = "Start a new game",
                            description = "Start a new game with the provided players and initial configuration.",
                            requestBody = @RequestBody(
                                    description = "Game start details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = RegisterGameRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Game started successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = Response.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid input data")
                            },
                            security = @SecurityRequirement(name = "BearerAuth")
                    )
            ),
            @RouterOperation(
                    path = "/game/movement",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"game"},
                            operationId = "makeMovement",
                            summary = "Make a move in the game",
                            description = "Perform a movement in the current game by specifying the origin and destination positions.",
                            requestBody = @RequestBody(
                                    description = "Movement details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = MovementRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Movement executed successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = Response.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid movement data"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Game not found")
                            },
                            security = @SecurityRequirement(name = "BearerAuth")
                    )
            )
    })
    public RouterFunction<ServerResponse> accountRoutes() {
        return route(POST("/game/start"), gameHandler::startGame)
                .andRoute(POST("/game/movement"), gameHandler::makeMovement);
    }
}