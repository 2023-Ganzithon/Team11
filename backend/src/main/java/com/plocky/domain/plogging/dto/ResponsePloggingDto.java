package com.plocky.domain.plogging.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponsePloggingDto {
    private Long ploggingId;
    private float distance;
    private int totalHour;
    private int totalMinute;
    private int totalSecond;
    private String startedWhere;
    private String endedWhere;
    private TrashDto trash;
}
