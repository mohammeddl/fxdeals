package com.progressoft.fxdeals.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.repository.FxDealRepository;
import com.progressoft.fxdeals.service.impl.FxDealServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FxDealServiceTest {

@Mock FxDealRepository repo;
@InjectMocks FxDealServiceImpl service;

    
@Test
void importDeals_allValid_allSaved() {
    FxDealRequest d1 = new FxDealRequest(
            UUID.randomUUID(), "USD", "EUR",
            OffsetDateTime.now(), BigDecimal.valueOf(1_000));

    FxDealRequest d2 = new FxDealRequest(
            UUID.randomUUID(), "GBP", "JPY",
            OffsetDateTime.now(), BigDecimal.valueOf(250));

    when(repo.existsById(any())).thenReturn(false);

    service.importDeals(List.of(d1, d2));

    verify(repo, times(2)).save(argThat(e ->
            Set.of(d1.id(), d2.id()).contains(e.getId())));
}
@Test
void importDeals_duplicate_skipped() {
    UUID duplicateId = UUID.randomUUID();
    FxDealRequest request = new FxDealRequest(
            duplicateId, "CAD", "CHF",
            OffsetDateTime.now(), BigDecimal.valueOf(300));

    when(repo.existsById(duplicateId)).thenReturn(true);

    service.importDeals(List.of(request));

    verify(repo, never()).save(any());
    verify(repo).existsById(duplicateId);
}
// @Test
// void importDeals_partialFailure_firstPersists() {
//     FxDealRequest ok   = new FxDealRequest(
//             UUID.randomUUID(), "CHF", "USD",
//             OffsetDateTime.now(), BigDecimal.valueOf(900.50));

//     FxDealRequest boom = new FxDealRequest(
//             UUID.randomUUID(), "AUD", "NZD",
//             OffsetDateTime.now(), BigDecimal.valueOf(123.45));

//     when(repo.existsById(any()))
//             .thenReturn(false)  
//             .thenReturn(false); 

//     doNothing()
//         .doThrow(new DataIntegrityViolationException("Simulated DB failure"))
//         .when(repo).save(any(FxDeal.class));

//     assertThrows(DataIntegrityViolationException.class,
//                  () -> service.importDeals(List.of(ok, boom)));

//     verify(repo, times(2)).save(any(FxDeal.class));
//     verify(repo).save(argThat(e -> e.getId().equals(ok.id())));
// }

}
