package com.example.ggulddeokbe.domain.policy.service;

import com.example.ggulddeokbe.domain.policy.dto.PolicyDetailResponse;
import com.example.ggulddeokbe.global.exception.BaseException;
import com.example.ggulddeokbe.global.exception.ErrorCode;
import com.example.ggulddeokbe.infra.feign.client.YouthPolicyClient;
import com.example.ggulddeokbe.infra.gemini.GeminiSummaryService;
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
public class PolicyDetailQueryService {

    private static final String CACHE_PREFIX    = "policy:detail:";
    private static final long   CACHE_TTL_HOURS = 24;

    private final YouthPolicyClient youthPolicyClient;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final GeminiSummaryService geminiSummaryService;

    public PolicyDetailResponse getPolicyDetail(String plcyNo) {
        String cacheKey = CACHE_PREFIX + plcyNo;

        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return objectMapper.convertValue(cached, PolicyDetailResponse.class);
        }

        YouthPolicyResult result = youthPolicyClient.getPolicy(
                "2", "json", null, null, null, plcyNo, null
        ).result();

        List<YouthPolicyItem> items = result.youthPolicyList();
        if (items == null || items.isEmpty()) {
            throw new BaseException(ErrorCode.POLICY_NOT_FOUND);
        }

        PolicyDetailResponse response = PolicyDetailResponse.from(items.get(0));
        String aiDescription = geminiSummaryService.generateDescription(response);
        response = response.withAiDescription(aiDescription);

        redisTemplate.opsForValue().set(cacheKey, response, CACHE_TTL_HOURS, TimeUnit.HOURS);
        return response;
    }
}
