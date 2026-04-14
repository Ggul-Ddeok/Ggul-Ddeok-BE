package com.example.ggulddeokbe.domain.policy.controller;

import com.example.ggulddeokbe.domain.policy.domain.InterestArea;
import com.example.ggulddeokbe.domain.policy.domain.Region;
import com.example.ggulddeokbe.domain.policy.dto.PolicyDetailResponse;
import com.example.ggulddeokbe.domain.policy.dto.PolicyListResponse;
import com.example.ggulddeokbe.domain.policy.service.PolicyQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ggulddeok/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyQueryService policyQueryService;

    @GetMapping
    public ResponseEntity<PolicyListResponse> getPolicies(
            @RequestParam("region") Region region,
            @RequestParam(value = "interest", defaultValue = "전체") InterestArea interestArea
    ) {
        return ResponseEntity.ok(policyQueryService.getPolicies(region, interestArea));
    }

    @GetMapping("/{plcyNo}")
    public ResponseEntity<PolicyDetailResponse> getPolicyDetail(
            @PathVariable("plcyNo") String plcyNo
    ) {
        return ResponseEntity.ok(policyQueryService.getPolicyDetail(plcyNo));
    }
}
