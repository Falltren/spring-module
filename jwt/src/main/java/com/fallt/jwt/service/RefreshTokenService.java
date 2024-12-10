package com.fallt.jwt.service;

import com.fallt.jwt.domain.entity.RefreshToken;
import com.fallt.jwt.exception.EntityNotFoundException;
import com.fallt.jwt.exception.RefreshTokenException;
import com.fallt.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${app.security.jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> findByRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        var refreshToken = RefreshToken.builder()
                .userId(userId)
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration.toMillis()))
                .token(UUID.randomUUID().toString())
                .build();
        refreshToken = refreshTokenRepository.save(refreshToken);
        System.out.println(refreshToken.getId());
        return refreshToken;
    }

    public RefreshToken checkRefreshToken(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException(refreshToken.getToken(), "Refresh token was expired. Repeat login action");
        }
        return refreshToken;
    }

    public void deleteByUserId(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    public RefreshToken getRefreshTokenByUserId(Long id){
        return refreshTokenRepository.findByUserId(id).orElseThrow(
                () -> new RefreshTokenException("Refresh token by userId: " + id + " not found")
        );
    }
}
