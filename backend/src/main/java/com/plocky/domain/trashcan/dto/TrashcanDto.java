package com.plocky.domain.trashcan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter @Setter
@AllArgsConstructor
public class TrashcanDto {
    private Long trashcanId;
    private float latitude;
    private float longitude;
}
