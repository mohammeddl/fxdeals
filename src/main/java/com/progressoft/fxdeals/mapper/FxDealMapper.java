package com.progressoft.fxdeals.mapper;

import com.progressoft.fxdeals.dto.response.FxDealResponse;
import com.progressoft.fxdeals.model.FxDeal;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FxDealMapper {

    FxDealResponse toResponse(FxDeal entity);

    List<FxDealResponse> toResponseList(List<FxDeal> entities);
}
