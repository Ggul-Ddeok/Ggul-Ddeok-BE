package com.example.ggulddeokbe.infra.youth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record YouthPolicyItem(
    // 식별자
    @JsonProperty("plcyNo")           String plcyNo,

    // 정책 기본 정보
    @JsonProperty("plcyNm")           String plcyNm,
    @JsonProperty("plcyKywdNm")       String plcyKywdNm,
    @JsonProperty("plcyExplnCn")      String plcyExplnCn,
    @JsonProperty("plcySprtCn")       String plcySprtCn,
    @JsonProperty("lclsfNm")          String lclsfNm,
    @JsonProperty("mclsfNm")          String mclsfNm,

    // 지원 조건
    @JsonProperty("sprtTrgtMinAge")   String sprtTrgtMinAge,
    @JsonProperty("sprtTrgtMaxAge")   String sprtTrgtMaxAge,
    @JsonProperty("sprtSclCnt")       String sprtSclCnt,
    @JsonProperty("schoolCd")         String schoolCd,

    // 신청 관련
    @JsonProperty("aplyYmd")          String aplyYmd,
    @JsonProperty("aplyUrlAddr")      String aplyUrlAddr,
    @JsonProperty("plcyAplyMthdCn")   String plcyAplyMthdCn,
    @JsonProperty("sbmsnDcmntCn")     String sbmsnDcmntCn,
    @JsonProperty("srngMthdCn")       String srngMthdCn,

    // 참고 링크
    @JsonProperty("refUrlAddr1")      String refUrlAddr1,
    @JsonProperty("refUrlAddr2")      String refUrlAddr2,

    // 주관기관
    @JsonProperty("sprvsnInstCdNm")   String sprvsnInstCdNm,

    // 지역 필터링
    @JsonProperty("zipCd")            String zipCd,

    // 기타 조건
    @JsonProperty("addAplyQlfcCndCn") String addAplyQlfcCndCn,
    @JsonProperty("etcMttrCn")        String etcMttrCn
) {}
