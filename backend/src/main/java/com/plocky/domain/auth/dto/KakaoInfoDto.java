package com.plocky.domain.auth.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class KakaoInfoDto {
    @JsonProperty("id")
    private String kakaoId;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("app_id")
    private int appId;
}
