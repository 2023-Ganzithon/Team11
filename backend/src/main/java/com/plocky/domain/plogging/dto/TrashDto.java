package com.plocky.domain.plogging.dto;

import lombok.*;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrashDto {
    private int paperQuantity;
    private int plaQuantity;
    private int glassQuantity;
    private int canQuantity;
    private int foamQuantity;
    private int etcQuantity;
    private int cigarQuantity;
}
