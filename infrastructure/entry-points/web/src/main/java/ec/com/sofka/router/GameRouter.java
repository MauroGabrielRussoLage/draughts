package ec.com.sofka.router;

import ec.com.sofka.data.request.RegisterGameRequestDTO;
import ec.com.sofka.handler.GameHandler;
import ec.com.sofka.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

@Tag(name = "Account Management", description = "Endpoints for managing accounts")
@Configuration
public class GameRouter {

    private final GameHandler gameHandler;

    public GameRouter(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/accounts/create",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"accounts"},
                            operationId = "register",
                            summary = "Create a new account",
                            description = "Create a new account with the provided details",
                            requestBody = @RequestBody(
                                    description = "Account registration details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = RegisterGameRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Account created successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = Response.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid input data")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> accountRoutes() {
        return route(POST("/accounts/create"), gameHandler::startGame);
    }
}