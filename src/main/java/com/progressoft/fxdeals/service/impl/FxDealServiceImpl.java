package com.progressoft.fxdeals.service.impl;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;
import com.progressoft.fxdeals.model.FxDeal;
import com.progressoft.fxdeals.mapper.FxDealMapper;
import com.progressoft.fxdeals.repository.FxDealRepository;
import com.progressoft.fxdeals.service.FxDealService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository repo;
    private final FxDealMapper mapper;

    @Override
    public void importDeals(List<FxDealRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            log.warn("importDeals called with no requests");
            return;
        }

        for (FxDealRequest r : requests) {
            // r.id() is already a UUID (Bean Validation ensures non-null UUID)
            UUID dealId = r.id();

            // 1) Check for duplicate
            if (repo.existsById(dealId)) {
                log.info("Skipping duplicate FX deal with ID {}", dealId);
                continue;
            }

            // 2) Build FxDeal entity directly from request fields
            FxDeal entity = FxDeal.builder()
                    .id(dealId)
                    .fromCurrency(r.fromCurrency())
                    .toCurrency(r.toCurrency())
                    .dealTimestamp(r.dealTimestamp())
                    .amount(r.amount())
                    .build();

            // 3) Attempt to save; catch any data integrity exceptions to avoid rollback
            try {
                repo.save(entity);
                log.info("Inserted FX deal {}", dealId);
            } catch (DataIntegrityViolationException ex) {
                log.error("Data integrity violation when inserting FX deal {}: {}", dealId, ex.getMessage());
                // continue to next request; do not let one bad row roll back all previous inserts
            } catch (Exception ex) {
                log.error("Unexpected error when inserting FX deal {}: {}", dealId, ex.getMessage());
            }
        }
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<FxDealResponse> getAllDeals() {
        List<FxDeal> allEntities = repo.findAll();
        return mapper.toResponseList(allEntities);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public FxDealResponse getDealById(UUID id) {
        FxDeal entity = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FX deal not found: " + id));
        return mapper.toResponse(entity);
    }
}
