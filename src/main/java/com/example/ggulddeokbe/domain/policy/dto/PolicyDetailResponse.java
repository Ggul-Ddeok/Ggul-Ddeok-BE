package com.example.ggulddeokbe.domain.policy.dto;

import com.example.ggulddeokbe.infra.youth.dto.YouthPolicyItem;

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
        String schoolCode,
        String aiDescription
) {
    public static PolicyDetailResponse from(YouthPolicyItem item) {
        return new PolicyDetailResponse(
                item.plcyNo(),
                item.plcyNm(),
                item.plcyExplnCn(),
                item.plcySprtCn(),
                item.lclsfNm(),
                item.mclsfNm(),
                item.aplyYmd(),
                item.sprtSclCnt(),
                item.sprtTrgtMinAge(),
                item.sprtTrgtMaxAge(),
                item.plcyAplyMthdCn(),
                item.sbmsnDcmntCn(),
                item.srngMthdCn(),
                item.addAplyQlfcCndCn(),
                item.etcMttrCn(),
                item.aplyUrlAddr(),
                item.refUrlAddr1(),
                item.refUrlAddr2(),
                item.sprvsnInstCdNm(),
                item.zipCd(),
                item.schoolCd(),
                null
        );
    }

    public PolicyDetailResponse withAiDescription(String aiDescription) {
        return new PolicyDetailResponse(
                plcyNo, policyName, description, supportContent,
                largeCategoryName, mediumCategoryName, applicationPeriod, supportScale,
                minAge, maxAge, applicationMethod, requiredDocuments,
                screeningMethod, additionalQualification, otherMatters,
                applyUrl, refUrl1, refUrl2, organizationName, regionCode, schoolCode,
                aiDescription
        );
    }
}
