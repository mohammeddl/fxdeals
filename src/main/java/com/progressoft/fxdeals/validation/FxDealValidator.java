package com.progressoft.fxdeals.validation;

import com.progressoft.fxdeals.dto.request.FxDealRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import com.progressoft.fxdeals.exception.InvalidCurrencyException;
import com.progressoft.fxdeals.exception.ValidationException;

@Component
@Slf4j
public class FxDealValidator {
    private static final Pattern CUR_PATTERN = Pattern.compile("^[A-Z]{3}$");

    public void validate(FxDealRequest r) {
        log.debug("Validating FX deal request: {}", r);

        if (r.id() == null) {
            throw new ValidationException("Deal id must not be null");
        }
        if (r.dealTimestamp() == null) {
            throw new ValidationException("Deal timestamp must not be null");
        }
        BigDecimal amt = r.amount();
        if (amt == null || amt.signum() <= 0) {
            throw new ValidationException("Deal amount must be positive");
        }

        String from = r.fromCurrency();
        String to   = r.toCurrency();
        if (from == null || !CUR_PATTERN.matcher(from).matches()) {
            throw new InvalidCurrencyException("Invalid fromCurrency format");
        }
        if (to == null || !CUR_PATTERN.matcher(to).matches()) {
            throw new InvalidCurrencyException("Invalid toCurrency format");
        }
        if (from.equals(to)) {
            throw new InvalidCurrencyException(
                "fromCurrency and toCurrency must be different"
            );
        }

        log.debug("Validation passed for {}", r.id());
    }
}
