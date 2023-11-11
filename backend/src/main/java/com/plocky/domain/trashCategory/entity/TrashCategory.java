package com.plocky.domain.trashCategory.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Transactional
public class TrashCategory {
    //쓰레기통
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trash_category_id")
    private Long id;
    private int paperQuantity;
    private int plaQuantity;
    private int glassQuantity;
    private int canQuantity;
    private int foamQuantity;
    private int etcQuantity;
    private int cigarQuantity;

    public int getTotalQuantity() {
        return paperQuantity + plaQuantity + glassQuantity
                + canQuantity + foamQuantity + etcQuantity + cigarQuantity;
    }
}
