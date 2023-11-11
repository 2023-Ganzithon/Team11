package com.plocky.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter @Builder
public class RankingListDto {
    private Long memberId;
    private int petLevel;
    private String nickname;
    private float totalDistance;
}
