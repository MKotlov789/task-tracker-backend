package ru.mkotlov789.edu.pet.tasktrackerbackend.annotation;

import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * Custom annotation for validating passwords using the {@link PasswordValidator} class.
 *
 * This annotation can be applied to fields, types, or other annotations and is used to enforce password validation.
 * It specifies the message to be displayed when password validation fails and can be customized with groups and payloads.
 *
 * A valid password must meet the following criteria:
 * - At least one digit.
 * - At least one lowercase letter.
 * - At least one uppercase letter.
 * - At least one special character from (@, #, $, %, ^, &, +, =, !).
 * - No spaces.
 * - A minimum length of 8 characters.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "Invalid password. Password must contain:\\n"
            + "A valid password must contain:\n"
            + "- At least one digit.\n"
            + "- At least one lowercase letter.\n"
            + "- At least one uppercase letter.\n"
            + "- At least one special character from (@, #, $, %, ^, &, +, =, !).\n"
            + "- No spaces.\n"
            + "- A minimum length of 8 characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
