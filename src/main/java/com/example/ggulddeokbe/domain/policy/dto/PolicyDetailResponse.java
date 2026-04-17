package com.example.ggulddeokbe.domain.policy.dto;

import com.example.ggulddeokbe.infra.youth.dto.YouthPolicyItem;

public record PolicyDetailResponse(
    String plcyNo,
    String policyName,
    String description,
    String supportContent,
    String minAge,
    String maxAge,
    String requiredDocuments,
    String applyUrl,
    String refUrl1,
    String organizationName,
    String aiDescription
) {
    public static PolicyDetailResponse from(YouthPolicyItem item) {
        return new PolicyDetailResponse(
            item.plcyNo(),
            item.plcyNm(),
            item.plcyExplnCn(),
            item.plcySprtCn(),
            item.sprtTrgtMinAge(),
            item.sprtTrgtMaxAge(),
            item.sbmsnDcmntCn(),
            item.aplyUrlAddr(),
            item.refUrlAddr1(),
            item.sprvsnInstCdNm(),
            null
        );
    }

    public PolicyDetailResponse withAiDescription(String aiDescription) {
        return new PolicyDetailResponse(
            plcyNo, policyName, description, supportContent,
            minAge, maxAge, requiredDocuments,
            applyUrl, refUrl1, organizationName,
            aiDescription
        );
    }
}
