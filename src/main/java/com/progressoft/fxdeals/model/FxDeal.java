package com.progressoft.fxdeals.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FxDeal {
    @Id
    private UUID id;
    @Column(length = 3, nullable = false)
    private String fromCurrency;
    @Column(length = 3, nullable = false)
    private String toCurrency;
    @Column(nullable = false)
    private OffsetDateTime dealTimestamp;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;
}
