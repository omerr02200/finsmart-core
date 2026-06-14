package com.finsmart.core.finance.controller;

import com.finsmart.core.common.dto.ApiResponse;
import com.finsmart.core.finance.dto.AccountRequest;
import com.finsmart.core.finance.dto.AccountResponse;
import com.finsmart.core.finance.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ApiResponse<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ApiResponse.success("Hesap başarıyla oluşturuldu", response);
    }

    @GetMapping
    public ApiResponse<List<AccountResponse>> getUserAccounts() {
        List<AccountResponse> response = accountService.getUserAccounts();
        return ApiResponse.success("Hesaplar başarıyla listelendi", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getAccountById(@PathVariable UUID id) {
        AccountResponse response = accountService.getAccountById(id);
        return ApiResponse.success("Hesap detayı getirildi", response);
    }
}
