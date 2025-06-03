package com.progressoft.fxdeals.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;
import com.progressoft.fxdeals.service.FxDealService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class FxDealController {

    private final FxDealService service;


    @PostMapping("/import")
    public ResponseEntity<Void> importDeals(
            @RequestBody List<FxDealRequest> payload
    ) {
        service.importDeals(payload);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<FxDealResponse>> getAllDeals() {
        return ResponseEntity.ok(service.getAllDeals());

}

}
