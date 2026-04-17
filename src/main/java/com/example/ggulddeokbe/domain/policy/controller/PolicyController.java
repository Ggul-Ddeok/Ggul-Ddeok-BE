package com.example.ggulddeokbe.domain.policy.controller;

import com.example.ggulddeokbe.domain.policy.domain.InterestArea;
import com.example.ggulddeokbe.domain.policy.domain.Region;
import com.example.ggulddeokbe.domain.policy.dto.PolicyDetailResponse;
import com.example.ggulddeokbe.domain.policy.dto.PolicyListResponse;
import com.example.ggulddeokbe.domain.policy.service.PolicyDetailQueryService;
import com.example.ggulddeokbe.domain.policy.service.PolicyListQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Policy", description = "청년 정책 API")
@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyListQueryService policyListQueryService;
    private final PolicyDetailQueryService policyDetailQueryService;

    @Operation(summary = "정책 목록 조회", description = "지역과 관심 분야로 청년 정책 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<PolicyListResponse> getPolicies(
        @RequestParam("region") Region region,
        @RequestParam(value = "interest", defaultValue = "전체") InterestArea interestArea
    ) {
        return ResponseEntity.ok(policyListQueryService.getPolicies(region, interestArea));
    }

    @Operation(summary = "정책 상세 조회", description = "정책 번호로 특정 청년 정책의 상세 정보를 조회합니다.")
    @GetMapping("/{plcyNo}")
    public ResponseEntity<PolicyDetailResponse> getPolicyDetail(
        @PathVariable("plcyNo") String plcyNo
    ) {
        return ResponseEntity.ok(policyDetailQueryService.getPolicyDetail(plcyNo));
    }
}
