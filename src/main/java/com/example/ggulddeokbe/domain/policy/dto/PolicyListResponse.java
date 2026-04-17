package com.example.ggulddeokbe.domain.policy.dto;

import com.example.ggulddeokbe.infra.youth.dto.YouthPolicyItem;

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
    ) {
        public static PolicyItem from(YouthPolicyItem item) {
            return new PolicyItem(
                item.plcyNo(),
                item.plcyNm(),
                item.plcyKywdNm(),
                item.lclsfNm(),
                item.plcyExplnCn()
            );
        }
    }

    public static PolicyListResponse of(List<YouthPolicyItem> items, int totalCount) {
        List<PolicyItem> policies = items.stream()
            .map(PolicyItem::from)
            .toList();
        return new PolicyListResponse(totalCount, policies);
    }
}
