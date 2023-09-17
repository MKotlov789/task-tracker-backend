package ru.mkotlov789.edu.pet.tasktrackerbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @NotEmpty
    String username;
    @NotNull
    @NotEmpty
    String password;
}
