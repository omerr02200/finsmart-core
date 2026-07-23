package com.finsmart.core.analysis.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "financial_metrics")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal totalIncome = BigDecimal.ZERO;

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal totalExpense = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}
