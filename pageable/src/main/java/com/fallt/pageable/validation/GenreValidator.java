package com.fallt.pageable.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class GenreValidator implements ConstraintValidator<GenreValidation, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(GenreValidation annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean isValid = acceptedValues.contains(value.toString());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            String availableValues = String.join(", ", acceptedValues);
            context.buildConstraintViolationWithTemplate(
                    context.getDefaultConstraintMessageTemplate()
                            .replace("{availableValues}", availableValues)
            ).addConstraintViolation();
        }
        return isValid;
    }
}
