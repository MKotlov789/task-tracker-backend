package ru.mkotlov789.edu.pet.tasktrackerbackend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.mkotlov789.edu.pet.tasktrackerbackend.dto.EmailDto;


@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${topic}")
    private String topic;

    private final KafkaTemplate<String , Object> kafkaTemplate;


    public void sendWelcomeEmail(String emailAddress, String username) {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailAdress(emailAddress);
        emailDto.setSubject("Welcome!");
        emailDto.setBody("Welcome,"+username+"!");
        kafkaTemplate.send(topic,emailDto.getEmailAdress(),emailDto);

    }
}
