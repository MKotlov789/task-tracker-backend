package ru.mkotlov789.edu.pet.tasktrackerbackend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.LoginRequest;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.RegisterRequest;
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.AuthenticationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        log.info("start registr");
        authService.registerUser(registerRequest);
        return ResponseEntity.ok("User is registered");
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        log.info("start login");
        authService.authenticateUser(loginRequest);
        return  ResponseEntity.ok("LoginPage");
    }



//    @PostMapping("/login")
//    String doLogin() {
//        return "redirect:/tasks";
//    }



}
