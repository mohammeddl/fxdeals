package com.progressoft.fxdeals.controller;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;
import com.progressoft.fxdeals.service.FxDealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
@Validated
public class FxDealController {

    private final FxDealService service;


    @PostMapping("/import")
    public ResponseEntity<Void> importDeals(@RequestBody List<@Valid FxDealRequest> payload) {
        service.importDeals(payload);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<FxDealResponse>> getAllDeals() {
        List<FxDealResponse> list = service.getAllDeals();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FxDealResponse> getDealById(@PathVariable UUID id) {
        FxDealResponse dto = service.getDealById(id);
        return ResponseEntity.ok(dto);
    }
}
