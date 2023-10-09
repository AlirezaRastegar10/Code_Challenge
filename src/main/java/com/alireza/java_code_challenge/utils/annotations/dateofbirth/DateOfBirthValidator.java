package com.alireza.java_code_challenge.utils.annotations.dateofbirth;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, String> {

    @Override
    public void initialize(ValidDateOfBirth constraintAnnotation) {
    }

    @Override
    public boolean isValid(String dateOfBirthStr, ConstraintValidatorContext context) {
        if (dateOfBirthStr == null || dateOfBirthStr.isEmpty()) {
            return false;
        }

        try {
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            LocalDate currentDate = LocalDate.now();
            LocalDate maxBirthDate = currentDate.minusYears(100);

            // Check if the date is not in the future and not more than 100 years old
            return !dateOfBirth.isAfter(currentDate) && !dateOfBirth.isBefore(maxBirthDate);
        } catch (Exception e) {
            return false; // Invalid date format
        }
    }
}
