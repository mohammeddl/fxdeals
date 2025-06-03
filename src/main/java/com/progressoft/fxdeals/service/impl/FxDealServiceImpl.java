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
            UUID dealId = r.id();

            if (repo.existsById(dealId)) {
                log.info("Skipping duplicate FX deal with ID {}", dealId);
                continue;
            }
            FxDeal entity = FxDeal.builder()
                    .id(dealId)
                    .fromCurrency(r.fromCurrency())
                    .toCurrency(r.toCurrency())
                    .dealTimestamp(r.dealTimestamp())
                    .amount(r.amount())
                    .build();

            try {
                repo.save(entity);
                log.info("Inserted FX deal {}", dealId);
            } catch (DataIntegrityViolationException ex) {
                log.error("Data integrity violation when inserting FX deal {}: {}", dealId, ex.getMessage());
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
