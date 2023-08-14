package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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




    public String registerUser(String username, String password) throws UserExistsException{

        if (userRepository.existsUserByUsername(username)) {
            throw new UserExistsException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepository.save(user);
        return authenticateUser(username, password);
    }

    public String authenticateUser(String username, String password) throws AuthenticationException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                password
        );

        authenticationManager.authenticate(authentication);
        User user = userRepository.findByUsername(username).get();
        List<Role> roleList = new ArrayList<>();
        roleList.add(user.getRole());
        String jwtToken = jwtService.createToken(user.getUsername(),roleList);
        return jwtToken;

    }
}
