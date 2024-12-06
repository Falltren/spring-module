package com.fallt.pageable.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = GenreValidator.class)
public @interface GenreValidation {
    Class<? extends Enum<?>> enumClass();

    String message() default "Incorrect role. Available roles: {availableValues}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
