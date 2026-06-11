package com.finsmart.core.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @NotBlank(message = "Ad soyad alanı zorunludur")
        String fullname,

        @Email(message = "Lütfen geçerli bir e-posta adresi giriniz")
        @NotBlank(message = "E-posta alanı zorunludur")
        String email,

        @NotBlank(message = "Şifre alanı zorunludur")
        String password
) { }
