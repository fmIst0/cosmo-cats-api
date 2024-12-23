package com.example.cosmocatsapi.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CosmicWordCheckValidator implements ConstraintValidator<CosmicWordCheck, String> {
    private static final String VALID_PATTERN = "(?i)\\b(star|galaxy|comet)\\b";

    private static final Pattern pattern = Pattern.compile(VALID_PATTERN);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && pattern.matcher(value).find();
    }
}
