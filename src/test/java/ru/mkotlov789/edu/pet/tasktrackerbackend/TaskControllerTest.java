package ru.mkotlov789.edu.pet.tasktrackerbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.LoginRequest;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.TaskDto;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Role;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Task;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.TaskRepository;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.UserRepository;
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.JwtService;


import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    private final String USERNAME = "testuser";
    private final String EMAIL = "ahahahhah@ahahhah.ahah";
    private final String PASSWORD = "Zzzaaadfdw1!@";
    private String token;
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        taskRepository.deleteAll();
        objectMapper = new ObjectMapper();
        user = new User();
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setRole(Role.USER);
        userRepository.save(user);
        token = jwtService.createToken(USERNAME, List.of(Role.USER));


    }

    @Test
    public void testGetTasks() throws Exception {

        Task task = new Task(1L,
                "Test Task",
                "Test Description",
                false,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                user);

        taskRepository.save(task);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetTask() throws Exception {
        Task task = new Task(1L,
                "Test Task",
                "Test Description",
                false,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                user);

        taskRepository.save(task);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
