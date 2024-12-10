package com.fallt.jwt.controller;

import com.fallt.jwt.domain.dto.request.LoginRequest;
import com.fallt.jwt.domain.dto.request.RefreshTokenRequest;
import com.fallt.jwt.domain.dto.response.AuthResponse;
import com.fallt.jwt.domain.dto.response.RefreshTokenResponse;
import com.fallt.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    public String logout(@AuthenticationPrincipal UserDetails userDetails) {
        authService.logout();
        return MessageFormat.format("User with username: {0} logout", userDetails.getUsername());
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}
