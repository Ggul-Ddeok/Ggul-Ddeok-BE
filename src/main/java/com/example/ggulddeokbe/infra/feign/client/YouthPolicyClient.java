package com.example.ggulddeokbe.infra.feign.client;

import com.example.ggulddeokbe.infra.feign.config.FeignConfig;
import com.example.ggulddeokbe.infra.youth.dto.YouthPolicyApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "youth-policy",
        url = "https://www.youthcenter.go.kr",
        configuration = FeignConfig.class
)
public interface YouthPolicyClient {

    // pageType=1: 목록, pageType=2: 상세
    @GetMapping("/go/ythip/getPlcy")
    YouthPolicyApiResponse getPolicy(
            @RequestParam("pageType")   String pageType,
            @RequestParam("rtnType")    String rtnType,
            @RequestParam(value = "pageNum",  required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "zipCd",    required = false) String zipCd,
            @RequestParam(value = "plcyNo",   required = false) String plcyNo
    );

}
