package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.UserRepository;


@AllArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        if (isEmailAddress(usernameOrEmail)) {
            return userRepository.findByEmail(usernameOrEmail)
                    .orElseThrow(()-> new UsernameNotFoundException("user with email "+usernameOrEmail+ " is not found"));
        } else {
            return userRepository.findByUsername(usernameOrEmail)
                    .orElseThrow(()-> new UsernameNotFoundException("user with username "+usernameOrEmail+ " is not found"));
        }


    }
    public void deleteUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) loadUserByUsername( username);
        userRepository.delete(user);
    }

    private boolean isEmailAddress(String username) { return username.contains("@");}
}
