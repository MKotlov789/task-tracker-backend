package ru.mkotlov789.edu.pet.tasktrackerbackend.annotation;

import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * Custom annotation for validating usernames using the {@link UsernameValidator} class.
 *
 * This annotation can be applied to fields, types, or other annotations and is used to enforce username validation.
 * It specifies the message to be displayed when username validation fails and can be customized with groups and payloads.
 *
 * A valid username must meet the following criteria:
 * - Contain only alphanumeric characters, hyphens, and underscores.
 * - Be between 3 and 16 characters in length.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface ValidUsername {
    String message() default "Invalid username";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}