package com.example.ggulddeokbe.domain.bookmark.dto;

public record BookmarkRequest(
    String policyId,
    String policyName,
    String keywords,
    String largeCategoryName,
    String description
) {
}
