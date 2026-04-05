package com.example.ggulddeokbe.infra.feign.config;

import com.example.ggulddeokbe.infra.youth.properties.YouthPolicyProperties;
import com.example.ggulddeokbe.infra.feign.exception.FeignErrorDecoder;
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
        return template -> template.query("openApiVlak", youthPolicyProperties.getApiKey());
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }
}