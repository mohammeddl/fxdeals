package com.progressoft.fxdeals.service;

import java.util.List;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;


public interface FxDealService {

    void importDeals(List<FxDealRequest> requests);

    List<FxDealResponse> getAllDeals();
}
