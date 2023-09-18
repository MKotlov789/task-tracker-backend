package ru.mkotlov789.edu.pet.tasktrackerbackend.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.AuthenticationResponse;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.LoginRequest;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.RegisterRequest;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.UserExistsException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.AuthenticationService;
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.EmailService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authService;
    private final EmailService emailService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        try {
            String jwtToken = authService.registerUser(registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getEmail());
            emailService.sendWelcomeEmail(registerRequest.getEmail(), registerRequest.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
        } catch (UserExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            String jwtToken = authService.authenticateUser(loginRequest.getUsername(),loginRequest.getPassword());
            return  ResponseEntity.ok(new AuthenticationResponse(jwtToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.notFound().build();
        }
    }





//    @PostMapping("/login")
//    String doLogin() {
//        return "redirect:/tasks";
//    }



}
