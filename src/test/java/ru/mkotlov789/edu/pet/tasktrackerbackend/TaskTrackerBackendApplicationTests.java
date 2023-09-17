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
	String USERNAME = "user1";
	
	String PASSWORD = "pass";



}
