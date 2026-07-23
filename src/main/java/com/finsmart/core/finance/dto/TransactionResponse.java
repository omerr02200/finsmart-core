package com.finsmart.core.finance.dto;

import com.finsmart.core.finance.entities.TransactionCategory;
import com.finsmart.core.finance.entities.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID userId,
        UUID accountId,
        String accountName,
        TransactionType type,
        TransactionCategory category,
        BigDecimal amount,
        String description,
        LocalDate transactionDate,
        Instant createdAt) { }