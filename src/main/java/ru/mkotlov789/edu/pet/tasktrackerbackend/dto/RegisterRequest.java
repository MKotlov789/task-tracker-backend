package ru.mkotlov789.edu.pet.tasktrackerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String password;
}
