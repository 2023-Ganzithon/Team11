package com.plocky.domain.plogging.dto.kakaoMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class KakaoMapDocument {
    private KakaoMapRoadAddress road_address;
}