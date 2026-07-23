package com.finsmart.core.analysis.repositories;

import com.finsmart.core.analysis.entities.FinancialMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FinancialMetricRepository extends JpaRepository<FinancialMetric, UUID> {
    Optional<FinancialMetric> findByUserId(UUID userId);
}