package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.NotFoundException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.TaskCompletedException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Task;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.TaskRepository;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.UserRepository;

import java.sql.Timestamp;
import java.util.*;
@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;



    public void updateTask(Task task,User user, Long taskId) {
        log.info("updating is started");
        task.setId(taskId);
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Task existing = taskRepository.getReferenceById(task.getId());

        if(existing == null || existing.getUser().getId()!= user.getId()) {
            throw new NotFoundException("Task not found.");
        } else if (existing.isCompleted()) {
            throw new TaskCompletedException("Unable to update a complete task.");
        } else {
            copyNonNullProperties(task, existing);
            taskRepository.save(existing);
        }
    }
    public void saveTask(Task task, User user) {
        task.setUser(user);
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        taskRepository.save(task);
    }

    public List<Task> getTasks(User user) {
        return taskRepository.findByUserId(user.getId());
    }

    public Task getTask( User user, Long taskId) {
        Task task = taskRepository.getReferenceById(taskId);
        if(task.getUser().getId()!= user.getId()) {
            throw new NotFoundException();
        }
        return task;
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
