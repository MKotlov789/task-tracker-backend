package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.LoginRequest;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.LoginResponse;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.RegisterRequest;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.UserExistsException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Role;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;




    public void registerUser(RegisterRequest registerRequest) throws UserExistsException{

        if (userRepository.existsUserByUsername(registerRequest.getUsername())) {
            throw new UserExistsException();
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        log.info("authenth is created");
        Authentication authentication = authenticationManager.authenticate(
           auth
        );
        log.info("authenticated");
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .get();
        List<Role> roleList = new ArrayList<>();
        roleList.add(user.getRole());
        String jwtToken = jwtService.createToken(user.getUsername(),roleList);
        return new LoginResponse(jwtToken);

    }
}
