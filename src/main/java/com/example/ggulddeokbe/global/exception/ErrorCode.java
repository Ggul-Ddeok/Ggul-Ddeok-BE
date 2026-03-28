package com.example.ggulddeokbe.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    // Auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // OAuth
    KAKAO_ACCOUNT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "카카오 계정 정보를 찾을 수 없습니다."),
    KAKAO_PROFILE_NOT_FOUND(HttpStatus.UNAUTHORIZED, "카카오 프로필 정보를 찾을 수 없습니다."),
    OAUTH_EMAIL_NOT_FOUND(HttpStatus.UNAUTHORIZED, "OAuth 이메일 정보를 찾을 수 없습니다."),
    OAUTH_ACCOUNT_ID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "OAuth 계정 ID를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
