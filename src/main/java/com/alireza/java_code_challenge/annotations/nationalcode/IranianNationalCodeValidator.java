package com.alireza.java_code_challenge.annotations.nationalcode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class IranianNationalCodeValidator implements ConstraintValidator<IranianNationalCode, String> {

    private static final Pattern IRANIAN_NATIONAL_CODE_PATTERN = Pattern.compile("^[0-9]{10}$");

    @Override
    public void initialize(IranianNationalCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nationalCode, ConstraintValidatorContext context) {
        if (nationalCode == null || !IRANIAN_NATIONAL_CODE_PATTERN.matcher(nationalCode).matches()) {
            return false;
        }

        // Calculate the checksum of the Iranian national code
        int[] digits = nationalCode.chars().map(Character::getNumericValue).toArray();
        int checksum = 0;
        for (int i = 0; i < 9; i++) {
            checksum += digits[i] * (10 - i);
        }
        checksum %= 11;
        return checksum < 2 && digits[9] == checksum || checksum >= 2 && digits[9] == 11 - checksum;
    }
}
