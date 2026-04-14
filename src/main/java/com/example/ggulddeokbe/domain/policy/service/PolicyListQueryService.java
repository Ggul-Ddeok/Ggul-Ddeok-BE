package com.example.ggulddeokbe.domain.policy.service;

import com.example.ggulddeokbe.domain.policy.domain.InterestArea;
import com.example.ggulddeokbe.domain.policy.domain.Region;
import com.example.ggulddeokbe.domain.policy.dto.PolicyListResponse;
import com.example.ggulddeokbe.infra.feign.client.YouthPolicyClient;
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
public class PolicyListQueryService {

    private static final String CACHE_PREFIX  = "policies:";
    private static final long   CACHE_TTL_HOURS = 24;
    private static final int    PAGE_SIZE     = 100;

    private final YouthPolicyClient youthPolicyClient;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public PolicyListResponse getPolicies(Region region, InterestArea interestArea) {
        String cacheKey = CACHE_PREFIX + region.getCode() + ":" + interestArea.getCode();

        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return objectMapper.convertValue(cached, PolicyListResponse.class);
        }

        YouthPolicyResult result = youthPolicyClient.getPolicy(
                "1", "json", 1, PAGE_SIZE, region.getZipCd(), null, interestArea.getLclsfNm()
        ).result();

        List<YouthPolicyItem> items = result.youthPolicyList() != null ? result.youthPolicyList() : List.of();
        int totalCount = result.pagging() != null ? result.pagging().totCount() : 0;

        PolicyListResponse response = PolicyListResponse.of(items, totalCount);
        redisTemplate.opsForValue().set(cacheKey, response, CACHE_TTL_HOURS, TimeUnit.HOURS);
        return response;
    }
}
