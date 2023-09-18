package ru.mkotlov789.edu.pet.tasktrackerbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.AuthorizationException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.TaskDto;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.NotFoundException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.TaskCompletedException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Task;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for managing tasks.
 *
 * Authentication is enforced via the use of the `@AuthenticationPrincipal` annotation, which ensures that
 * each endpoint is accessible only to authenticated users, and the authenticated user's identity is available
 * for performing user-specific operations.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper modelMapper;
    @GetMapping("/")
    public ResponseEntity<List<TaskDto>> getTasks(@AuthenticationPrincipal User user){
        List<TaskDto> taskDtos = taskService.getTasks(user)
                .stream()
                .map(t -> taskToTaskDto(t))
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId,
                                           @AuthenticationPrincipal User user) {
        try {
            Task task = taskService.getTask(user, taskId);
            return ResponseEntity.ok(taskToTaskDto(task));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }


    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable Long taskId,
                                              @RequestBody TaskDto taskDto,
                                              @AuthenticationPrincipal User user) {
        Task taskUpdate = taskDtoToTask(taskDto);
        try {
            taskService.updateTask(taskUpdate, user, taskId);
            return ResponseEntity.ok("task with id '{}' is updated"+taskId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (TaskCompletedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId, @AuthenticationPrincipal User user) {
        try {
            taskService.deleteTask(taskId, user);
            return ResponseEntity.ok("task with id '{}' is removed"+taskId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AuthorizationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @PostMapping("/")
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto,
                                             @AuthenticationPrincipal User user) {
        Task task = taskDtoToTask( taskDto);
        taskService.saveTask(task, user);
        return ResponseEntity.ok("new task is created");
    }

    private TaskDto taskToTaskDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }
    private Task taskDtoToTask(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }
}
