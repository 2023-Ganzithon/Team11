package com.plocky.domain.member.dto;

import lombok.*;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingMeDto {
    private Long memberId;
    private int petLevel;
    private String nickname;
    private float totalDistance;
    private int ranking;
}
