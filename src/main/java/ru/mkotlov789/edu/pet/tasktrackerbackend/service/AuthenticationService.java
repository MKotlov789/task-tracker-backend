package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

import org.springframework.security.core.AuthenticationException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.UserExistsException;

public interface AuthenticationService {
    String registerUser(String username,
                        String password,
                        String email) throws UserExistsException;
    String authenticateUser(String identifier, String password) throws AuthenticationException;
}
