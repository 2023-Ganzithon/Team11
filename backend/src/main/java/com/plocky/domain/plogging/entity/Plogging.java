package com.plocky.domain.plogging.entity;

import com.plocky.domain.member.entity.Member;
import com.plocky.domain.trashCategory.entity.TrashCategory;
import com.plocky.global.entity.BaseTimeEntity;
import com.plocky.global.entity.Location;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Transactional
@EntityListeners(AuditingEntityListener.class)
public class Plogging extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="plogging_id")
    private Long id;
    private float distance;
    private int quantity;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "started_location_id")
    private Location startedLocation;
    private LocalDateTime startedWhen;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ended_location_id")
    private Location endedLocation;
    private LocalDateTime endedWhen;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trash_category_id")
    private TrashCategory trashCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
}
