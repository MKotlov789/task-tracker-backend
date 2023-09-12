package ru.mkotlov789.edu.pet.tasktrackerbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private String emailAdress;
    private String subject;
    private String body;
}
