package com.progressoft.fxdeals.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;
import com.progressoft.fxdeals.mapper.FxDealMapper;
import com.progressoft.fxdeals.model.FxDeal;
import com.progressoft.fxdeals.repository.FxDealRepository;
import com.progressoft.fxdeals.service.FxDealService;
import com.progressoft.fxdeals.validation.FxDealValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository repo;
    private final FxDealMapper mapper;

    @Override
    public void importDeals(List<FxDealRequest> requests) {
        for (FxDealRequest req : requests) {
            FxDealValidator.validate(req);
            if (repo.existsById(req.id())) {
                log.info("Skipping duplicate FX deal with ID {}", req.id());
                continue;
            }
            FxDeal entity = FxDeal.builder()
                .id(req.id())
                .fromCurrency(req.fromCurrency())
                .toCurrency(req.toCurrency())
                .dealTimestamp(req.dealTimestamp())
                .amount(req.amount())
                .build();

            try {
                repo.save(entity);
                log.info("Inserted FX deal {}", entity.getId());
            } catch (DataIntegrityViolationException ex) {
                
                log.error("Data integrity violation when inserting FX deal {}: {}",
                        entity.getId(), ex.getMessage());
            }
        }
    }

    @Override
    public List<FxDealResponse> getAllDeals() {
        List<FxDeal> allEntities = repo.findAll();
        return mapper.toResponseList(allEntities);
    }

}
