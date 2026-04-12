package com.example.ggulddeokbe.domain.policy.dto;

public record PolicyDetailResponse(
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
        String applicationMethod,
        String requiredDocuments,
        String screeningMethod,
        String additionalQualification,
        String otherMatters,
        String applyUrl,
        String refUrl1,
        String refUrl2,
        String organizationName,
        String regionCode,
        String schoolCode
) {}
