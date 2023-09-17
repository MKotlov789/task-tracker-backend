package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mkotlov789.edu.pet.tasktrackerbackend.exception.UserExistsException;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Role;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;





    public String registerUser(String username,
                               String password,
                               String email) throws UserExistsException{

        if (userRepository.existsUserByUsername(username) ||
            userRepository.existsUserByEmail(email)) {
            throw new UserExistsException();
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("User "+ username + " is registered");
        return authenticateUser(username, password);
    }

    public String authenticateUser(String username, String password) throws AuthenticationException {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        log.info("authenticating");
        authenticationManager.authenticate(authentication);
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            //If the user is not found by username, we se
            userOptional = userRepository.findByEmail(username);
        }
        User user = userOptional.get();
        List<Role> roleList = new ArrayList<>();
        roleList.add(user.getRole());
        String jwtToken = jwtService.createToken(user.getUsername(),roleList);
        return jwtToken;

    }


}
