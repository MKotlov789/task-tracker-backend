package ru.mkotlov789.edu.pet.tasktrackerbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ru.mkotlov789.edu.pet.tasktrackerbackend.controller.AuthenticationController;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.LoginRequest;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.RegisterRequest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskTrackerBackendApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;
	String USERNAME = "user";
	String PASSWORD = "pass";

	@Test
	void LoginTest() {

		LoginRequest loginRequest = new LoginRequest(USERNAME,PASSWORD);
		RegisterRequest registerRequest = new RegisterRequest(USERNAME,PASSWORD);

		ResponseEntity<String> registerEntry = restTemplate.postForEntity(
				"/api/auth/login",
				registerRequest,
				String.class);

		ResponseEntity<String> logEntry = restTemplate.postForEntity(
				"/api/auth/login",
				loginRequest,
				String.class);

		assertThat(logEntry.getBody()).isEqualTo("LoginPage");
	}

}
