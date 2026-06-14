package com.finsmart.core.finance.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountRequest(
        @NotBlank(message = "Hesap adı zorunludur")
        String name
) {
}