package ru.mkotlov789.edu.pet.tasktrackerbackend.annotation;

import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "Invalid password. Password must contain:\\n"
            + "A valid password must contain:\n"
            + "At least one digit.\n"
            + "At least one lowercase letter.\n"
            + "At least one uppercase letter.\n"
            + "At least one special character from (@, #, $, %, ^, &, +, =, !).\n"
            + "No spaces.\n"
            + "A minimum length of 8 characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
