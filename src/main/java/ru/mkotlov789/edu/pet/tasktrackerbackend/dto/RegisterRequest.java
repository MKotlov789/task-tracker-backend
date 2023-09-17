package ru.mkotlov789.edu.pet.tasktrackerbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import ru.mkotlov789.edu.pet.tasktrackerbackend.annotation.ValidEmail;


@Data
@AllArgsConstructor
public class RegisterRequest {
    @ValidEmail
    @NotNull
    @NotEmpty
    String email;
    @NotNull
    @NotEmpty
    String username;
    @NotNull
    @NotEmpty
    String password;


}
