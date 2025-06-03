package com.progressoft.fxdeals.service;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;

import java.util.List;
import java.util.UUID;


public interface FxDealService {

    void importDeals(List<FxDealRequest> requests);

    List<FxDealResponse> getAllDeals();

    FxDealResponse getDealById(UUID id);
}
