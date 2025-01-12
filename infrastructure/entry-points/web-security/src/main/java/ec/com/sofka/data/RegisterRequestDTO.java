package ec.com.sofka.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
    @NotBlank(message = "Name cannot be empty")
    @Schema(description = "First name of the user", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "Last name cannot be empty")
    @Schema(description = "Last name of the user", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastname;

    @NotBlank(message = "Username cannot be empty")
    @Schema(description = "Username for login", example = "johndoe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "Password for login", example = "securePassword123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotEmpty(message = "Roles cannot be empty")
    @Schema(description = "Roles of the user", example = "[ADMIN, CUSTOMER] or [CUSTOMER]", allowableValues = {"ADMIN", "CUSTOMER"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> roles;
}
