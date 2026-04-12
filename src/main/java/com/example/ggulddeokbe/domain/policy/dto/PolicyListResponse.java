package com.example.ggulddeokbe.domain.policy.dto;

import java.util.List;

public record PolicyListResponse(
        int totalCount,
        List<PolicyItem> policies
) {
    public record PolicyItem(
            String plcyNo,
            String policyName,
            String description,
            String supportContent,
            String largeCategoryName,
            String mediumCategoryName,
            String applicationPeriod,
            String supportScale,
            String minAge,
            String maxAge,
            String applyUrl,
            String refUrl1,
            String refUrl2,
            String organizationName,
            String regionCode,
            String schoolCode
    ) {}
}
