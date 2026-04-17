package com.example.ggulddeokbe.infra.youth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record YouthPolicyResult(
    @JsonProperty("pagging")        YouthPolicyPagging pagging,
    @JsonProperty("youthPolicyList") List<YouthPolicyItem> youthPolicyList
) {}
