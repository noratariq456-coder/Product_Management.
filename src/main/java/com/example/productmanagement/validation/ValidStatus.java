package com.example.productmanagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation.
 * Ensures the "status" field of a Product is one of the allowed values:
 * AVAILABLE, OUT_OF_STOCK, DISCONTINUED
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidator.class)
public @interface ValidStatus {

    String message() default "Status must be one of: AVAILABLE, OUT_OF_STOCK, DISCONTINUED";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
