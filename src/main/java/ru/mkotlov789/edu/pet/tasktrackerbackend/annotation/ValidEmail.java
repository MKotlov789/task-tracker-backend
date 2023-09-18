package ru.mkotlov789.edu.pet.tasktrackerbackend.annotation;




import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.*;

import jakarta.validation.Constraint;


import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom annotation for validating email addresses using the {@link EmailValidator} class.
 *
 * This annotation can be applied to fields, types, or other annotations and is used to enforce email address validation.
 * It specifies the message to be displayed when email validation fails and can be customized with groups and payloads.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "Invalid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
