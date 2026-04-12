package com.example.ggulddeokbe.domain.policy.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Region {

    전체(null,    "ALL"),
    서울("11000", "SEOUL"),
    부산("26000", "BUSAN"),
    대구("27000", "DAEGU"),
    인천("28000", "INCHEON"),
    광주("29000", "GWANGJU"),
    대전("30000", "DAEJEON"),
    울산("31000", "ULSAN"),
    세종("36000", "SEJONG"),
    경기("41000", "GYEONGGI"),
    강원("51000", "GANGWON"),
    충북("43000", "CHUNGBUK"),
    충남("44000", "CHUNGNAM"),
    전북("52000", "JEONBUK"),
    전남("46000", "JEONNAM"),
    경북("47000", "GYEONGBUK"),
    경남("48000", "GYEONGNAM"),
    제주("50000", "JEJU");

    private final String zipCd;
    private final String code;
}
