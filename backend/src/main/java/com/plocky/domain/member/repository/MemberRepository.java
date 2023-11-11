package com.plocky.domain.member.repository;

import com.plocky.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoId(String kakaoId);
    Optional<Member> findByNickname(String nickname);

    @Query("select count(m) + 1 from Member m where (m.totalDistance > :totalDistance) "
    + "or (m.totalDistance = :totalDistance and m.nickname < :nickname)")
    int calculateRanking(@Param("totalDistance") float totalDistance, @Param("nickname") String nickname);

    @Query("select m from Member m order by m.totalDistance desc, m.nickname asc")
    List<Member> findByRanking();
}
