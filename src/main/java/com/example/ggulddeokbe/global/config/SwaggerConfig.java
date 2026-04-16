package com.example.ggulddeokbe.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ggul-Ddeok API")
                        .description("Ggul-Ddeok 청년 정책 서비스 API 문서")
                        .version("v1.0.0"));
    }
}
