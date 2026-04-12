package com.example.ggulddeokbe.infra.youth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "youth")
public record YouthPolicyProperties(String apiKey) {}
