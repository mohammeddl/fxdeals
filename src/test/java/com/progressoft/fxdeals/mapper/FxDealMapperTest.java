package com.progressoft.fxdeals.mapper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.progressoft.fxdeals.dto.response.FxDealResponse;
import com.progressoft.fxdeals.model.FxDeal;

class FxDealMapperTest {

    private FxDealMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(FxDealMapper.class);
    }

    @Test
    void toResponse_mapsAllFieldsCorrectly() {
        UUID id = UUID.randomUUID();
        String from = "USD";
        String to = "EUR";
        OffsetDateTime ts = OffsetDateTime.now();
        BigDecimal amt = new BigDecimal("123.45");

        FxDeal entity = new FxDeal(id, from, to, ts, amt);

        FxDealResponse dto = mapper.toResponse(entity);

        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.fromCurrency()).isEqualTo(from);
        assertThat(dto.toCurrency()).isEqualTo(to);
        assertThat(dto.dealTimestamp()).isEqualTo(ts);
        assertThat(dto.amount()).isEqualByComparingTo(amt);
    }

    @Test
    void toResponseList_mapsListOfEntities() {
        FxDeal e1 = new FxDeal(
                UUID.randomUUID(), "GBP", "JPY", OffsetDateTime.now(), new BigDecimal("50.00")
        );
        FxDeal e2 = new FxDeal(
                UUID.randomUUID(), "AUD", "CAD", OffsetDateTime.now(), new BigDecimal("75.75")
        );

        List<FxDealResponse> dtos = mapper.toResponseList(List.of(e1, e2));

        assertThat(dtos).hasSize(2);
        assertThat(dtos)
                .extracting(FxDealResponse::fromCurrency)
                .containsExactlyInAnyOrder("GBP", "AUD");
    }
}
