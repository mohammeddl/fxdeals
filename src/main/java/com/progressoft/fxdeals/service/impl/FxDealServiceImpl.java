package com.progressoft.fxdeals.service.impl;
import org.springframework.stereotype.Service;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;
import com.progressoft.fxdeals.mapper.FxDealMapper;
import com.progressoft.fxdeals.model.FxDeal;
import com.progressoft.fxdeals.repository.FxDealRepository;
import com.progressoft.fxdeals.service.FxDealService;
import com.progressoft.fxdeals.validation.FxDealValidator;
import com.progressoft.fxdeals.exception.RequestAlreadyExistException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository repo;
    private final FxDealMapper mapper;
    private final FxDealValidator validator;

    @Override
    public FxDealResponse importDeal(FxDealRequest dto) {
        validator.validate(dto);

        log.info("Importing deal: id={}, from={}, to={}, amount={}",
                dto.id(), dto.fromCurrency(), dto.toCurrency(), dto.amount());

        if (repo.existsById(dto.id())) {
            log.warn("Duplicate ID detected: {}. Aborting.", dto.id());
            throw new RequestAlreadyExistException("Deal with ID " + dto.id() + " already exists");
        }
        FxDeal entity = mapper.toEntity(dto);
        FxDeal saved = repo.save(entity);
        return mapper.toResponse(saved);
    }

}
