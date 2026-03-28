package com.example.ggulddeokbe.infra.oauth.service;

import com.example.ggulddeokbe.domain.user.domain.User;
import com.example.ggulddeokbe.domain.user.domain.repository.UserRepository;
import com.example.ggulddeokbe.global.exception.BaseException;
import com.example.ggulddeokbe.global.exception.ErrorCode;
import com.example.ggulddeokbe.global.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return process(oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount == null) {
            throw new BaseException(ErrorCode.KAKAO_ACCOUNT_NOT_FOUND);
        }

        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        if (kakaoProfile == null) {
            throw new BaseException(ErrorCode.KAKAO_PROFILE_NOT_FOUND);
        }

        String email = (String) kakaoAccount.get("email");
        if (email == null) {
            throw new BaseException(ErrorCode.OAUTH_EMAIL_NOT_FOUND);
        }

        String nickname = (String) kakaoProfile.get("nickname");
        if (nickname == null) {
            throw new BaseException(ErrorCode.OAUTH_ACCOUNT_ID_NOT_FOUND);
        }

        User user = userRepository.findByEmail(email)
            .orElseGet(() -> userRepository.save(
                User.builder()
                    .email(email)
                    .nickname(nickname)
                    .build()
            ));

        return new AuthDetails(user, attributes);
    }
}
