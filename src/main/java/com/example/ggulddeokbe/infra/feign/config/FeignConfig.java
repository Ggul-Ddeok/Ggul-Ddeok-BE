package com.example.ggulddeokbe.infra.feign.config;

import com.example.ggulddeokbe.infra.youth.properties.YouthPolicyProperties;
import com.example.ggulddeokbe.infra.feign.exception.FeignErrorDecoder;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    private final YouthPolicyProperties youthPolicyProperties;

    @Bean
    public RequestInterceptor youthApiKeyInterceptor() {
        return template -> template.query("apiKeyNm", youthPolicyProperties.getApiKey());
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}