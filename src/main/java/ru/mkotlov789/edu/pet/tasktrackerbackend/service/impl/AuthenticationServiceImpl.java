package ru.mkotlov789.edu.pet.tasktrackerbackend.service.impl;

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
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.AuthenticationService;
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.JwtService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;




    /**
     * Registers a new user with the provided username, password, and email address.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @param email    The email address of the new user.
     * @return A JWT token representing the authenticated user.
     * @throws UserExistsException If a user with the same username or email already exists.
     */
    @Override
    public String registerUser(String username,
                               String password,
                               String email) throws UserExistsException{

        if (userRepository.existsUserByUsername(username)) {
            throw new UserExistsException("User with this username exists");
        } else if (userRepository.existsUserByEmail(email)) {
            throw new UserExistsException("User with this email exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("User "+ username + " is registered");
        String jwtToken = jwtService.createToken(user.getUsername(),List.of(Role.USER));
        return jwtToken;
    }

    /**
     * Authenticates a user with the provided username or email and password.
     *
     * @param identifier The username or email of the user to authenticate.
     * @param password   The password of the user to authenticate.
     * @return A JWT token representing the authenticated user.
     * @throws AuthenticationException If authentication fails.
     */
    @Override
    public String authenticateUser(String identifier, String password) throws AuthenticationException {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                identifier,
                password
        );
        log.info("authenticating");
        authenticationManager.authenticate(authentication);
        Optional<User> userOptional = userRepository.findByUsername(identifier);

        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByEmail(identifier);
        }
        User user = userOptional.get();
        String jwtToken = jwtService.createToken(user.getUsername(),List.of(user.getRole()));
        return jwtToken;

    }


}
