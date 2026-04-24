package com.example.ggulddeokbe.domain.bookmark.dto;

import jakarta.validation.constraints.NotBlank;

public record BookmarkRequest(
    @NotBlank(message = "정책 ID는 필수입니다.") String policyId,
    @NotBlank(message = "정책 이름은 필수입니다.") String policyName,
    String keywords,
    String largeCategoryName,
    String description
) {
}
