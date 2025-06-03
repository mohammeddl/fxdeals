package com.progressoft.fxdeals.mapper;

import org.mapstruct.Mapper;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;
import com.progressoft.fxdeals.model.FxDeal;


@Mapper(componentModel = "spring")
public interface FxDealMapper {
    
    FxDeal toEntity(FxDealRequest dto);

    FxDealResponse toResponse(FxDeal deal);
}
