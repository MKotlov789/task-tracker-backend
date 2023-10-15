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
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.Role;
import ru.mkotlov789.edu.pet.tasktrackerbackend.model.User;
import ru.mkotlov789.edu.pet.tasktrackerbackend.repository.UserRepository;


import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    private final String USERNAME = "testuser";
    private final String EMAIL = "ahahahhah@ahahhah.ahah";
    private final String PASSWORD = "Zzzaaadfdw1!@";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

    }

    @Test
    public void testRegistration() throws Exception {
        String jsonRequest = "{\"username\":\""+USERNAME+
                "\", \"email\":\""+EMAIL+
                "\", \"password\":\""+PASSWORD+"\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        User user = userRepository.findByUsername(USERNAME).get();
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(EMAIL);
    }

    @Test
    public void testValidation() throws Exception {
        // JSON-представление с недопустимыми данными пользователя
        String jsonRequest = "{\"username\":\"invalidusername\"," +
                " \"email\":\"invalidemail\"," +
                " \"password\":\"short\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findByUsername("invalidusername");
        assertThat(user.isEmpty()).isTrue();
    }


    @Test
    public void testAuthenticationResponseContainsToken() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setRole(Role.USER);
        userRepository.save(user);
        assertThat(!userRepository.findByUsername(USERNAME).isEmpty());

        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwtToken").exists());
    }

    @Test
    public void testAccessWithToken() throws Exception {
        User user = new User();
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setRole(Role.USER);
        userRepository.save(user);

        String jsonRequest = "{\"username\":\""+USERNAME+
                "\", \"password\":\""+PASSWORD+"\"}";
        String authenticationResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        String jwtToken = objectMapper.readTree(authenticationResponse).get("jwtToken").textValue();


        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
