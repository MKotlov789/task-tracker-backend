package ru.mkotlov789.edu.pet.tasktrackerbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.AuthorizationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.NotFoundException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.TaskCompletedException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Task;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.TaskRepository;
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.TaskService;

import java.sql.Timestamp;
import java.util.*;
@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    /**
     * Updates a task with the provided information.
     *
     * This method is responsible for updating an existing task with the details provided in the 'taskUpdate' parameter.
     * It checks if the task with the specified 'taskId' exists, belongs to the 'user' performing the update, and is not already completed.
     * If the task passes these checks, its properties are updated based on the 'taskUpdate' parameter, and the changes are saved to the repository.
     *
     * @param taskUpdate The task containing the updated information.
     * @param user       The user performing the task update.
     * @param taskId     The ID of the task to be updated.
     *
     * @throws NotFoundException      If the specified task is not found in the repository.
     * @throws TaskCompletedException If the task is already completed and cannot be updated.
     */
    @Override
    public void updateTask(Task taskUpdate,User user, Long taskId) {
        log.info(": Task updating is started for task-id +"+taskId);
        taskUpdate.setId(taskId);
        taskUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Task updatedTask = taskRepository.getReferenceById(taskUpdate.getId());

        if(updatedTask == null || updatedTask.getUser().getId()!= user.getId()) {
            throw new NotFoundException("Task not found.");
        } else if (updatedTask.isCompleted()) {
            throw new TaskCompletedException("Unable to update a complete task.");
        } else {
            copyNonNullProperties(taskUpdate, updatedTask);
            taskRepository.save(updatedTask);
        }
    }
    @Override
    public void saveTask(Task task, User user) {
        task.setUser(user);
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        taskRepository.save(task);
    }
    @Override
    public List<Task> getTasks(User user) {
        return taskRepository.findByUserId(user.getId());
    }
    @Override
    public Task getTask( User user, Long taskId) {
        Task task = taskRepository.getReferenceById(taskId);
        if(task.getUser().getId()!= user.getId()) {
            throw new NotFoundException();
        }
        return task;
    }
    @Override
    public void deleteTask(Long taskId, User user) {
        Task taskToDelete = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found."));
        // Perform authorization checks if necessary
        if (taskToDelete.getUser().getId()!= user.getId()) {
            throw new AuthorizationException("User is not authorized to delete the task.");
        }
        // Delete the task
        taskRepository.delete(taskToDelete);
    }

    private  void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
