package org.docurest.infra;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Validator {

    private final jakarta.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public void assertValid(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
