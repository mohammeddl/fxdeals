package com.progressoft.fxdeals.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.model.FxDeal;
import com.progressoft.fxdeals.repository.FxDealRepository;
import com.progressoft.fxdeals.service.FxDealService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class FxDealServiceImpl implements FxDealService {
    private final FxDealRepository repo;

    @Override
    public void importDeals(List<FxDealRequest> requests) {
        requests.forEach(r -> {
            if (repo.existsById(r.id())) {
                return;
            }
            FxDeal entity = FxDeal.builder()
                .id(r.id())
                .fromCurrency(r.fromCurrency())
                .toCurrency(r.toCurrency())
                .dealTimestamp(r.dealTimestamp())
                .amount(r.amount())
                .build();
            repo.save(entity);
        });
    }
}