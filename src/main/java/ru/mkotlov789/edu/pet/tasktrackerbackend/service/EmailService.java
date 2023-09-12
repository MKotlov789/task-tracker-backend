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


    public void sendWelcomeEmail(String emailAdress) {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailAdress(emailAdress);
        emailDto.setSubject("Welcome!");
        emailDto.setBody("Welcome,"+emailAdress+"!");
        kafkaTemplate.send(topic,emailDto.getEmailAdress(),emailDto);


    }
}
