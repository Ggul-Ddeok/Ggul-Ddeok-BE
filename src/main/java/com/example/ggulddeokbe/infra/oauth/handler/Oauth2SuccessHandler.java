package com.example.ggulddeokbe.infra.oauth.handler;

import com.example.ggulddeokbe.global.security.auth.AuthDetails;
import com.example.ggulddeokbe.global.security.jwt.JwtTokenProvider;
import com.example.ggulddeokbe.global.security.jwt.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${frontend.redirect-uri}")
    private String frontendRedirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
        TokenResponse tokenResponse = jwtTokenProvider.createToken(authDetails.getUsername());

        long accessExpMs = tokenResponse.accessExp().toInstant(ZoneOffset.UTC).toEpochMilli();
        String redirectUrl = frontendRedirectUri
            + "?access_token=" + tokenResponse.accessToken()
            + "&access_exp=" + accessExpMs;
        response.sendRedirect(redirectUrl);
    }
}
