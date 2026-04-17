package com.example.ggulddeokbe.domain.bookmark.dto;

import com.example.ggulddeokbe.domain.bookmark.domain.Bookmark;

import java.time.LocalDateTime;

public record BookmarkResponse(
    Long id,
    String policyId,
    String policyName,
    String keywords,
    String largeCategoryName,
    String description,
    LocalDateTime createdAt
) {
    public static BookmarkResponse from(Bookmark bookmark) {
        return new BookmarkResponse(
            bookmark.getId(),
            bookmark.getPolicyId(),
            bookmark.getPolicyName(),
            bookmark.getKeywords(),
            bookmark.getLargeCategoryName(),
            bookmark.getDescription(),
            bookmark.getCreatedAt()
        );
    }
}
