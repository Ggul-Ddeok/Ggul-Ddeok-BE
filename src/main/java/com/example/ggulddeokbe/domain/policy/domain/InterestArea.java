package com.example.ggulddeokbe.domain.policy.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterestArea {

    전체(null,             "ALL"),
    일자리("일자리",         "ILJARI"),
    주거("주거",            "JUGEO"),
    교육("교육･직업훈련",    "GYOYUK"),
    금융복지문화("금융･복지･문화", "GEUMYUNG"),
    참여기반("참여･기반",     "CHAMYEO");

    private final String lclsfNm;  // API에 전송할 값 (null이면 미전송 → 전체)
    private final String code;     // 캐시 키용
}
