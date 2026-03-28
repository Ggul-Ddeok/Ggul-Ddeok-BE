package com.example.ggulddeokbe.global.security.jwt;

import java.time.LocalDateTime;

public record TokenResponse(
    String accessToken,
    LocalDateTime accessExp
) {
}
