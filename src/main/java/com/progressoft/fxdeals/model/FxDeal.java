package com.progressoft.fxdeals.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class FxDeal {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String fromCurrency;
    @Column(nullable = false)
    private String toCurrency;
    @Column(nullable = false)
    private OffsetDateTime dealTimestamp;
    @Column(nullable = false)
    private BigDecimal amount;
}
