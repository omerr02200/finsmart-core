package com.finsmart.core.finance.controller;

import com.finsmart.core.common.dto.ApiResponse;
import com.finsmart.core.finance.dto.TransactionRequest;
import com.finsmart.core.finance.dto.TransactionResponse;
import com.finsmart.core.finance.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ApiResponse<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.createTransaction(request);
        return ApiResponse.success("İşlem başarıyla eklendi", response);
    }

    @GetMapping
    public ApiResponse<Page<TransactionResponse>> getAccountTransactions(
            @PathVariable UUID accountId,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<TransactionResponse> response = transactionService.getAccountTransactions(accountId, pageable);
        return ApiResponse.success("İşlemler listelendi", response);
    }
}
