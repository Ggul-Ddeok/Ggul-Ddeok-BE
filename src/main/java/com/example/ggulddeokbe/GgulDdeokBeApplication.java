package com.example.ggulddeokbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GgulDdeokBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GgulDdeokBeApplication.class, args);
    }

}
