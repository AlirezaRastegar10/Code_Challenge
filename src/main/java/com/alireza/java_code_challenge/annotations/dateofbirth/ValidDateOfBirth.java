package com.alireza.java_code_challenge.annotations.dateofbirth;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateOfBirthValidator.class)
@Documented
public @interface ValidDateOfBirth {
    String message() default "Invalid date of birth";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
