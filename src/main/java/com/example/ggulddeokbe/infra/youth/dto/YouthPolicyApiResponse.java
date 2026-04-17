package com.example.ggulddeokbe.infra.youth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record YouthPolicyApiResponse(
    @JsonProperty("resultCode")    int resultCode,
    @JsonProperty("resultMessage") String resultMessage,
    @JsonProperty("result")        YouthPolicyResult result
) {}
