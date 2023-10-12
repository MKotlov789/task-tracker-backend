package ru.mkotlov789.edu.pet.tasktrackerbackend.service;

public interface EmailService {
    void sendWelcomeEmail(String emailAddress, String username);
}
