package com.finsmart.core.finance.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String name,
        BigDecimal balance,
        Instant createdAt
) { }