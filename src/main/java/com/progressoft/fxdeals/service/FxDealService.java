package com.progressoft.fxdeals.service;

import java.util.List;

import com.progressoft.fxdeals.dto.request.FxDealRequest;

public interface FxDealService {

   public void importDeals(List<FxDealRequest> requests); 
} 