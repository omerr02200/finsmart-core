package com.finsmart.core.auth.controller;

import com.finsmart.core.auth.dto.AuthResponse;
import com.finsmart.core.auth.dto.LoginRequest;
import com.finsmart.core.auth.dto.RegisterRequest;
import com.finsmart.core.auth.services.AuthService;
import com.finsmart.core.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ApiResponse.success("Kullanıcı başarıyla kaydedildi", response);
    }

    @GetMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ApiResponse.success("Giriş işlemi başarılı", response);
    }
}
