package ru.mkotlov789.edu.pet.tasktrackerbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    Long Id;
    private String title;
    private String description;
    private boolean isCompleted;
    private Timestamp updatedAt;

    private Timestamp createdAt;
}
