package com.example.ggulddeokbe.infra.youth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record YouthPolicyPagging(
    @JsonProperty("totCount") int totCount,
    @JsonProperty("pageNum")  int pageNum,
    @JsonProperty("pageSize") int pageSize
) {}
