package com.plocky.domain.plogging.dto.kakaoMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class KakaoMapResponse {
    private KakaoMapMeta meta;
    private List<KakaoMapDocument> documents;

    @Getter
    private static class KakaoMapMeta {
        private int total_count;
    }
}