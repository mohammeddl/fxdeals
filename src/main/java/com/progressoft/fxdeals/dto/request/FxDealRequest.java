package com.progressoft.fxdeals.dto.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record FxDealRequest(
        @NotNull
    UUID id,
    @NotNull
    String fromCurrency,
    @NotNull
    String toCurrency, 
    @NotNull
    OffsetDateTime dealTimestamp,
        @Positive(message="amount must be > 0")
        @NotNull
        BigDecimal amount
) {
    
}
