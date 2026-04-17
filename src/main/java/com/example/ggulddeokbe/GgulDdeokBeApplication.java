package com.example.ggulddeokbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableFeignClients
@EnableJpaAuditing
public class GgulDdeokBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GgulDdeokBeApplication.class, args);
    }

}
