package com.example.ggulddeokbe.domain.user.dto;

public record UserInfoResponse(
        Long id,
        String email,
        String nickname
) {
}