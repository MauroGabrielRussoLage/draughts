package ec.com.sofka.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDTO {
    @NotBlank(message = "Username cannot be empty")
    @NotNull(message = "Username cannot be null")
    @Schema(description = "Username for login", example = "johndoe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @NotNull(message = "Password cannot be null")
    @Schema(description = "Password for login", example = "securePassword123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
