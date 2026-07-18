package com.example.productmanagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StatusValidator implements ConstraintValidator<ValidStatus, String> {

    private static final List<String> ALLOWED_STATUSES =
            List.of("AVAILABLE", "OUT_OF_STOCK", "DISCONTINUED");

    @Override
    public void initialize(ValidStatus constraintAnnotation) {
        // no initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            // Let @NotBlank handle the "empty" case; this validator only checks allowed values
            return true;
        }
        return ALLOWED_STATUSES.contains(value);
    }
}
