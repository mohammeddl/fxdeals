package com.progressoft.fxdeals.validation;

import java.util.Set;

import com.progressoft.fxdeals.dto.request.FxDealRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class FxDealValidator {

    private static final ValidatorFactory factory = 
        Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();
    public static void validate(FxDealRequest req) {
        Set<ConstraintViolation<FxDealRequest>> violations = 
            validator.validate(req);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
