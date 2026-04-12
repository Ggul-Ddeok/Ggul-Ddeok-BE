package com.example.ggulddeokbe.domain.policy.service;

import com.example.ggulddeokbe.domain.policy.domain.Region;
import com.example.ggulddeokbe.domain.policy.dto.PolicyDetailResponse;
import com.example.ggulddeokbe.domain.policy.dto.PolicyListResponse;
import com.example.ggulddeokbe.global.exception.BaseException;
import com.example.ggulddeokbe.global.exception.ErrorCode;
import com.example.ggulddeokbe.infra.feign.client.YouthPolicyClient;
import com.example.ggulddeokbe.infra.youth.dto.YouthPolicyApiResponse;
import com.example.ggulddeokbe.infra.youth.dto.YouthPolicyItem;
import com.example.ggulddeokbe.infra.youth.dto.YouthPolicyResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PolicyQueryService {

    private static final String LIST_CACHE_PREFIX   = "policies:";
    private static final String DETAIL_CACHE_PREFIX = "policy:detail:";
    private static final long   CACHE_TTL_HOURS     = 24;
    private static final int    PAGE_SIZE           = 100;
    private static final String PAGE_TYPE_LIST      = "1";
    private static final String PAGE_TYPE_DETAIL    = "2";
    private static final String RTN_TYPE_JSON       = "json";

    private final YouthPolicyClient youthPolicyClient;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public PolicyListResponse getPolicies(Region region) {
        String cacheKey = LIST_CACHE_PREFIX + region.getCode();

        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return objectMapper.convertValue(cached, PolicyListResponse.class);
        }

        YouthPolicyApiResponse apiResponse = youthPolicyClient.getPolicy(
                PAGE_TYPE_LIST, RTN_TYPE_JSON, 1, PAGE_SIZE, region.getZipCd(), null
        );

        YouthPolicyResult result = apiResponse.result();
        List<YouthPolicyItem> items = result.youthPolicyList() != null ? result.youthPolicyList() : List.of();

        int totalCount = result.pagging() != null ? result.pagging().totCount() : 0;
        PolicyListResponse response = toListResponse(items, totalCount);
        redisTemplate.opsForValue().set(cacheKey, response, CACHE_TTL_HOURS, TimeUnit.HOURS);
        return response;
    }

    public PolicyDetailResponse getPolicyDetail(String plcyNo) {
        String cacheKey = DETAIL_CACHE_PREFIX + plcyNo;

        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return objectMapper.convertValue(cached, PolicyDetailResponse.class);
        }

        YouthPolicyApiResponse apiResponse = youthPolicyClient.getPolicy(
                PAGE_TYPE_DETAIL, RTN_TYPE_JSON, null, null, null, plcyNo
        );

        YouthPolicyResult result = apiResponse.result();
        List<YouthPolicyItem> items = result.youthPolicyList();
        if (items == null || items.isEmpty()) {
            throw new BaseException(ErrorCode.POLICY_NOT_FOUND);
        }

        PolicyDetailResponse response = toDetailResponse(items.get(0));
        redisTemplate.opsForValue().set(cacheKey, response, CACHE_TTL_HOURS, TimeUnit.HOURS);
        return response;
    }

    private PolicyListResponse toListResponse(List<YouthPolicyItem> items, int totalCount) {
        List<PolicyListResponse.PolicyItem> policies = items.stream()
                .map(item -> new PolicyListResponse.PolicyItem(
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
                        item.aplyUrlAddr(),
                        item.refUrlAddr1(),
                        item.refUrlAddr2(),
                        item.sprvsnInstCdNm(),
                        item.zipCd(),
                        item.schoolCd()
                ))
                .toList();
        return new PolicyListResponse(totalCount, policies);
    }

    private PolicyDetailResponse toDetailResponse(YouthPolicyItem item) {
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
                item.schoolCd()
        );
    }
}
