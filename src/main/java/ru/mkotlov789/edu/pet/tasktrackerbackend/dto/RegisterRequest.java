package ru.mkotlov789.edu.pet.tasktrackerbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import ru.mkotlov789.edu.pet.tasktrackerbackend.annotation.ValidEmail;
import ru.mkotlov789.edu.pet.tasktrackerbackend.annotation.ValidPassword;
import ru.mkotlov789.edu.pet.tasktrackerbackend.annotation.ValidUsername;

/**
 * A data class representing a user registration request.
 *
 * This class contains user registration data, including email, username, and password.
 * The fields are annotated with custom validation annotations:
 * - {@link ValidEmail} for email validation
 * - {@link ValidUsername} for username validation
 * - {@link ValidPassword} for password validation
 *
 * The class is designed for use in user registration processes and provides the necessary validation constraints.
 */
@Data
@AllArgsConstructor
public class RegisterRequest {
    @ValidEmail
    @NotNull
    @NotEmpty
    String email;
    @ValidUsername
    @NotNull
    @NotEmpty
    String username;
    @ValidPassword
    @NotNull
    @NotEmpty
    String password;


}
