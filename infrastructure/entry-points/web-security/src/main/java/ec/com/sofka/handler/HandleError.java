package ec.com.sofka.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class HandleError {

    protected static Mono<ServerResponse> handle(ServerRequest request, String errorCode, HttpStatus status, String message) {
        return ServerResponse.status(status)
                .bodyValue(new ErrorResponse(errorCode, status.value(), message));
    }

    static class ErrorResponse {
        private final String errorCode;
        private final int status;
        private final String message;

        public ErrorResponse(String errorCode, int status, String message) {
            this.errorCode = errorCode;
            this.status = status;
            this.message = message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
