package com.alireza.java_code_challenge.annotations.nationalcode;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IranianNationalCodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IranianNationalCode {
    String message() default "Invalid Iranian National Code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
