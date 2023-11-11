package com.plocky.domain.pet.entity;

import com.plocky.domain.member.entity.Member;
import com.plocky.domain.trashCategory.entity.TrashCategory;
import com.plocky.global.entity.BaseTimeEntity;
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
public class Pet extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;
    private String petName;
    @Enumerated(EnumType.STRING)
    private PetKind petKind;
    private int level;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void levelUp() {
        int[] distances = {1, 5, 10, 30, 50};
        int newLevel = 0;
        for (int i = 0; i < distances.length; i++) {
            if (member.getTotalDistance() > distances[i]) {
                newLevel = i + 1;
            }
        }
        this.level = newLevel;
    }
}