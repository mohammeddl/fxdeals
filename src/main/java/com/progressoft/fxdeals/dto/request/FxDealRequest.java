package com.progressoft.fxdeals.dto.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record FxDealRequest(
    @NotNull(message = "ID cannot be null")
    UUID id,
    @Pattern(regexp = "^[A-Z]{3}$")
    String fromCurrency, 
    @Pattern(regexp = "^[A-Z]{3}$")
    String toCurrency, 
    @NotNull
    OffsetDateTime dealTimestamp, 
    @DecimalMin("0.0")
    BigDecimal amount
) {
    
}
