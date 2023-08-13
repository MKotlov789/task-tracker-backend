package ru.mkotlov789.edu.pet.tasktrackerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaskController {
    @GetMapping("/tasks")
    public ResponseEntity<String> getTasks(){
        return ResponseEntity.ok("here will be some tasks");
    }
}
