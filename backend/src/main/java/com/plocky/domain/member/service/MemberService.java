package com.plocky.domain.member.service;

import com.plocky.domain.auth.dto.KakaoInfoDto;
import com.plocky.domain.auth.dto.TokenResponse;
import com.plocky.domain.member.dto.RankingListDto;
import com.plocky.domain.member.dto.RankingMeDto;
import com.plocky.domain.member.entity.Member;
import com.plocky.domain.member.repository.MemberRepository;
import com.plocky.domain.pet.entity.Pet;
import com.plocky.domain.pet.repository.PetRepository;
import com.plocky.domain.pet.service.PetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final PetService petService;
    private final Random random;
    @Value("${plocky.nickname.first}")
    private List<String> firstNick;
    @Value("${plocky.nickname.second}")
    private List<String> secondNick;

    // 랜덤한 닉네임 생성
    private String createRandomNickname(){
        int randomFirstIndex = random.nextInt(firstNick.size());
        int randomSecondIndex = random.nextInt(secondNick.size());
        return firstNick.get(randomFirstIndex) + secondNick.get(randomSecondIndex);
    }

    // 닉네임 중복 검사
    private void isValidNickname(Member member){
        while(true){
            if (memberRepository.findByNickname(member.getNickname()).orElse(null) == null) return ;
            else member.updateNickname(createRandomNickname());
        }
    }

    // 회원가입
    public void signup(KakaoInfoDto kakaoInfoDto, TokenResponse token) {
        // 닉네임 생성 및 검증
        String nickname = createRandomNickname();
        // member 생성
        Member member = Member.builder()
                .kakaoId(kakaoInfoDto.getKakaoId().toString())
                .refreshToken(token.getRefreshToken())
                .nickname(nickname)
                .totalDistance(0)
                .totalQuantity(0)
                .build();
        // 닉네임 중복 검사
        isValidNickname(member);
        memberRepository.save(member);
        // 펫 생성
        petService.createPet(member);
    }

    // 내 순위 정보 조회
    // 내 순위 or 상위 세개의 정보를 알기 위해서 모든 데이터를 불러오는 것이 타당한가...? -> Redis Sorted Set 이용하여 구현 가능하다고 함
    public RankingMeDto getRankingMe(String kakaoId) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new NullPointerException("Member not found for kakaoId: " + kakaoId));
        int ranking = memberRepository.calculateRanking(member.getTotalDistance(), member.getNickname());
        Pet pet = petRepository.findByMember(member)
                .orElseThrow(() -> new NullPointerException("Pet not found for member: " + member));

        return RankingMeDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .totalDistance(member.getTotalDistance())
                .petLevel(pet.getLevel())
                .ranking(ranking)
                .build();
    }

    // 랭킹 리스트, 단 회원정보가 세개 미만일 때는 존재하는 데이터만 반환
    public List<RankingListDto> getRankingList() {
        List<RankingListDto> members = memberRepository.findByRanking().stream()
                .limit(3)
                .map(member -> {
                    return RankingListDto
                            .builder()
                            .memberId(member.getId())
                            .petLevel(member.getPet().getLevel())
                            .nickname(member.getNickname())
                            .totalDistance(member.getTotalDistance())
                            .build();
                })
                .collect(Collectors.toList());
        return members;
    }
}
