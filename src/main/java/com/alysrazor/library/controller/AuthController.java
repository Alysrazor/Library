package com.alysrazor.library.controller;

import com.alysrazor.library.dto.request.LoginRequest;
import com.alysrazor.library.dto.request.RegisterRequest;
import com.alysrazor.library.dto.response.TokenResponse;
import com.alysrazor.library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(
            @RequestBody final RegisterRequest request) {
        final TokenResponse token = service.register(request);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(
            @RequestBody final LoginRequest request) {
        final TokenResponse token = service.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        return service.refreshToken(authHeader);
    }
}
