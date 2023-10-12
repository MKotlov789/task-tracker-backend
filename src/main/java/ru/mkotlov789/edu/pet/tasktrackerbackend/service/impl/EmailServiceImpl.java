package ru.mkotlov789.edu.pet.tasktrackerbackend.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.EmailDto;
import ru.mkotlov789.edu.pet.tasktrackerbackend.service.EmailService;

import java.util.concurrent.CompletableFuture;
/**
 * EmailService is a service class responsible for sending welcome email notifications using Kafka messaging.
 * It sends a welcome email with the provided email address and username to the specified Kafka topic.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${topic}")
    private String topic;

    private final KafkaTemplate<String , Object> kafkaTemplate;

    /**
     * Sends a welcome email notification to the specified email address with the provided username.
     *
     * @param emailAddress The recipient's email address.
     * @param username     The username for the welcome message.
     */
    @Override
    public void sendWelcomeEmail(String emailAddress, String username) {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailAdress(emailAddress);
        emailDto.setSubject("Welcome!");
        emailDto.setBody("Welcome,"+username+"!");
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic,emailDto.getEmailAdress(),emailDto);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Email message sent to Kafka topic '{}' for '{}'", topic, emailAddress);
            }
            else {
                log.error("Error sending email message to Kafka topic '{}' for '{}': {}", topic, emailAddress, ex.getMessage());
            }
        });

    }
}
