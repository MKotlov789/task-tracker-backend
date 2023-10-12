package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Task;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;

import java.util.List;

public interface TaskService {
    void updateTask(Task taskUpdate, User user, Long taskId);
    void saveTask(Task task, User user);
    List<Task> getTasks(User user);
    Task getTask( User user, Long taskId);
    void deleteTask(Long taskId, User user);
}
