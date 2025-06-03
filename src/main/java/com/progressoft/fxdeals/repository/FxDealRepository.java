package com.progressoft.fxdeals.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progressoft.fxdeals.model.FxDeal;

@Repository
public interface FxDealRepository extends JpaRepository<FxDeal, UUID> {
}
