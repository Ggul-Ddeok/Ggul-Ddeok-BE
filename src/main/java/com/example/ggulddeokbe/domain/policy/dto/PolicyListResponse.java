package com.example.ggulddeokbe.domain.policy.dto;

import java.util.List;

public record PolicyListResponse(
        int totalCount,
        List<PolicyItem> policies
) {
    public record PolicyItem(
            String plcyNo,
            String policyName,
            String keywords,
            String largeCategoryName,
            String description
    ) {}
}
