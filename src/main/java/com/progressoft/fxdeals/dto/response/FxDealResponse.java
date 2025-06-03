package com.progressoft.fxdeals.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record FxDealResponse(
        UUID id,
        String fromCurrency,
        String toCurrency,
        OffsetDateTime dealTimestamp,
        BigDecimal amount
) { }
