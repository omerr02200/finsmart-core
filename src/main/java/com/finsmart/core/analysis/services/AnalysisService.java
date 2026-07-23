package com.finsmart.core.analysis.services;

import com.finsmart.core.analysis.dto.AnalysisDashboardResponse;
import com.finsmart.core.analysis.entities.FinancialMetric;
import com.finsmart.core.analysis.repositories.FinancialMetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final FinancialMetricRepository metricsRepository;

    @Transactional
    public void updateFinancialMetrics(UUID userId, String type, BigDecimal amount) {
        log.info("Kullanıcının analiz verileri güncelleniyor. Type: {}, Tutar: {}", type, amount);

        FinancialMetric metric = metricsRepository.findByUserId(userId)
                .orElseGet(() -> FinancialMetric.builder().userId(userId).build());

        if("EXPENSE".equals(type)) {
            metric.setTotalExpense(metric.getTotalExpense().add(amount));
            log.info("Analiz Modülü: Gider bütçeye işlendi. Güncel toplam Gider: {}", metric.getTotalExpense());
        } else if("INCOME".equals(type)) {
            metric.setTotalIncome(metric.getTotalIncome().add(amount));
            log.info("Analiz Modülü: Gelir bütçeye işlendi. Güncel toplam Gelir: {}", metric.getTotalIncome());
        }

        metricsRepository.save(metric);
    }

    @Transactional(readOnly = true)
    public AnalysisDashboardResponse getDashboard(UUID userId) {
        FinancialMetric metric = metricsRepository.findByUserId(userId)
                .orElseGet(() -> FinancialMetric.builder()
                        .userId(userId)
                        .totalIncome(BigDecimal.ZERO)
                        .totalExpense(BigDecimal.ZERO)
                        .build());
        BigDecimal netMargin = metric.getTotalIncome().subtract(metric.getTotalExpense());

        return new AnalysisDashboardResponse(
                metric.getUserId(),
                metric.getTotalIncome(),
                metric.getTotalExpense(),
                netMargin
        );
    }
}
