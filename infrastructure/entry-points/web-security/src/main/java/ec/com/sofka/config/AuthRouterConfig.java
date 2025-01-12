package ec.com.sofka.config;

import ec.com.sofka.data.AuthRequestDTO;
import ec.com.sofka.data.RegisterRequestDTO;
import ec.com.sofka.handler.AuthHandler;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Tag(name = "Auth Management", description = "Endpoints for managing auth")
@Configuration
public class AuthRouterConfig {

    private final AuthHandler authHandler;

    public AuthRouterConfig(AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/auth/v1/login",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"auth"},
                            operationId = "login",
                            summary = "User Login",
                            description = "Receives the user's credentials (e.g., email and password).",
                            requestBody = @RequestBody(
                                    description = "Credentials required to log in",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AuthRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Login successful",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = Response.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "Invalid credentials or unauthorized user"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/auth/v1/register",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"auth"},
                            operationId = "register",
                            summary = "Create a New User",
                            description = "Creates a new user in the system with the provided data.",
                            requestBody = @RequestBody(
                                    description = "User registration details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = RegisterRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "User successfully created",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = Response.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error or invalid input data"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> authRoutes() {
        return route(POST("/auth/v1/login").and(accept(MediaType.APPLICATION_JSON)), authHandler::login)
                .andRoute(POST("/auth/v1/register").and(accept(MediaType.APPLICATION_JSON)), authHandler::register);
    }
}
