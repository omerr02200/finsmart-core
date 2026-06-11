package com.finsmart.core.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "Lütfen geçerli bir e-posta adresi giriniz")
        @NotBlank(message = "E-posta alanı zorunludur")
        String email,

        @NotBlank(message = "Şifre alanı zorunludur")
        String password
) { }