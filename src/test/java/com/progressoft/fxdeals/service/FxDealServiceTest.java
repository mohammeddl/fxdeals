
package com.progressoft.fxdeals.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.model.FxDeal;
import com.progressoft.fxdeals.repository.FxDealRepository;
import com.progressoft.fxdeals.service.impl.FxDealServiceImpl;

class FxDealServiceTest {

    @Mock
    private FxDealRepository repo;
    @InjectMocks
    private FxDealServiceImpl service;

    private UUID existingId;
    private UUID newId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        existingId = UUID.randomUUID();
        newId = UUID.randomUUID();
    }

    @Test
    void whenDuplicateId_thenSkipSave() {
        FxDealRequest request = new FxDealRequest(
                existingId,
                "USD",
                "EUR",
                OffsetDateTime.now(),
                new BigDecimal("100.00")
        );

        when(repo.existsById(existingId)).thenReturn(true);

        service.importDeals(List.of(request));

        verify(repo, never()).save(any(FxDeal.class));
    }

    @Test
    void whenNewId_thenSaveIsCalled() {
        FxDealRequest request = new FxDealRequest(
                newId,
                "GBP",
                "JPY",
                OffsetDateTime.now(),
                new BigDecimal("250.50")
        );

        when(repo.existsById(newId)).thenReturn(false);

        service.importDeals(List.of(request));

        // Check that save(...) was called exactly once with an FxDeal matching our request
        verify(repo, times(1)).save(argThat(entity ->
                entity.getId().equals(newId) &&
                entity.getFromCurrency().equals("GBP") &&
                entity.getToCurrency().equals("JPY") &&
                entity.getAmount().compareTo(new BigDecimal("250.50")) == 0
        ));
    }

    @Test
    void whenSaveThrowsDataIntegrityViolation_thenContinueNext() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        FxDealRequest r1 = new FxDealRequest(
                id1, "AUD", "CAD", OffsetDateTime.now(), new BigDecimal("300.00")
        );
        FxDealRequest r2 = new FxDealRequest(
                id2, "NZD", "CHF", OffsetDateTime.now(), new BigDecimal("150.00")
        );

    
        when(repo.existsById(id1)).thenReturn(false);
        when(repo.existsById(id2)).thenReturn(false);

        
        doThrow(new DataIntegrityViolationException("constraint violation"))
                .when(repo).save(argThat(e -> e.getId().equals(id2)));

        service.importDeals(List.of(r1, r2));

       
        verify(repo, times(2)).save(any(FxDeal.class));
        
    }

    @Test
    void whenGetDealByIdNotExists_thenThrow() {
        when(repo.findById(existingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getDealById(existingId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("FX deal not found");
    }
}
