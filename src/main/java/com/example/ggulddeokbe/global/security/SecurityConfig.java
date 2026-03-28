package com.example.ggulddeokbe.global.security;

import com.example.ggulddeokbe.global.exception.GlobalExceptionFilter;
import com.example.ggulddeokbe.global.security.jwt.JwtFilter;
import com.example.ggulddeokbe.global.security.jwt.JwtTokenProvider;
import com.example.ggulddeokbe.infra.oauth.handler.Oauth2FailureHandler;
import com.example.ggulddeokbe.infra.oauth.handler.Oauth2SuccessHandler;
import com.example.ggulddeokbe.infra.oauth.service.CustomOauth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(
        HttpSecurity http, CustomOauth2UserService customUserService,
        Oauth2SuccessHandler successHandler, Oauth2FailureHandler failureHandler,
        ObjectMapper objectMapper
    ) throws Exception {
        return http
            .csrf(CsrfConfigurer::disable)
            .cors(CorsConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(configurer -> configurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userinfo -> userinfo.userService(customUserService))
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .redirectionEndpoint(
                    endpoint -> endpoint
                        .baseUri("/ggulddeok/oauth/{registrationId}")
                )
            )
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .anyRequest().authenticated())
            .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new GlobalExceptionFilter(objectMapper), JwtFilter.class)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
