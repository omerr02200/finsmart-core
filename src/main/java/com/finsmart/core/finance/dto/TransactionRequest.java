package com.finsmart.core.finance.dto;

import com.finsmart.core.finance.entities.TransactionCategory;
import com.finsmart.core.finance.entities.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionRequest(
        @NotNull(message = "İşlem yapılacak hesap seçilmelidir")
        UUID accountId,

        @NotNull(message = "İşlem tipi (GELİR/GİDER) zorunludur")
        TransactionType type,

        @NotNull(message = "İşlem kategorisi zorunludur")
        TransactionCategory category,

        @NotNull(message = "Tutar alanı zorunludur")
        @DecimalMin(value = "0.01", message = "İşlem tutarı 0'dan büyük olmalıdır")
        BigDecimal amount,

        @NotNull(message = "Lütfen işlem için bir açıklama giriniz")
        String description,

        @NotNull(message = "İşlem tarihi belirtilmelidir")
        LocalDate transactionDate
) { }
