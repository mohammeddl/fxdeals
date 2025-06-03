package com.progressoft.fxdeals.service;


import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;


public interface FxDealService {

    FxDealResponse importDeal(FxDealRequest request);

}
