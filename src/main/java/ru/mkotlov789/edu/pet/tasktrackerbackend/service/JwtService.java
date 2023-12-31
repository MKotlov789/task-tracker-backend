package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Role;

import java.util.List;

public interface JwtService {
    boolean validateToken(String jwtToken);
    String getTokenFromRequest(HttpServletRequest request);
    UsernamePasswordAuthenticationToken authenticate(String validJwtToken);
    String createToken(String username, List<Role> roles);
}
