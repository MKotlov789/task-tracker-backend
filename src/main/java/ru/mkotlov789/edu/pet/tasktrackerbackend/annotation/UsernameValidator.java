package ru.mkotlov789.edu.pet.tasktrackerbackend.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator
        implements ConstraintValidator<ValidUsername, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,16}$";

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return validateUsername(username);
    }

    private boolean validateUsername(String username) {
        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
