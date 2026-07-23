package com.finsmart.core.analysis.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AnalysisDashboardResponse(
        UUID userId,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal netMargin
) {
}
