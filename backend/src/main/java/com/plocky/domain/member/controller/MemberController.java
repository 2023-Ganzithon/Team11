package com.plocky.domain.member.controller;

import com.plocky.domain.member.dto.RankingListDto;
import com.plocky.domain.member.dto.RankingMeDto;
import com.plocky.domain.member.repository.MemberRepository;
import com.plocky.domain.member.service.MemberService;
import com.plocky.global.jwt.service.JwtService;
import com.plocky.global.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

    @GetMapping("members/ranking/me")
    public RankingMeDto getRankingMe(HttpServletRequest request, HttpServletResponse response) {
        String extractedKakaoId = jwtService.extractKakaoId(jwtService.extractAccessToken(request).orElseThrow()).orElseThrow();

        if (SecurityUtil.getLoginedUserName().equals(extractedKakaoId)) {
            RankingMeDto rankingMeDto = memberService.getRankingMe(extractedKakaoId);
            return rankingMeDto;
        } else {
            response.setStatus(401);
            return null;
        }
    }

    @GetMapping("members/ranking")
    public List<RankingListDto> getRankingList(HttpServletRequest request, HttpServletResponse response) {
        String extractedKakaoId = jwtService.extractKakaoId(jwtService.extractAccessToken(request).orElseThrow()).orElseThrow();

        if (SecurityUtil.getLoginedUserName().equals(extractedKakaoId)) {
            List<RankingListDto> rankingList = memberService.getRankingList();
            response.setStatus(200);
            return rankingList;
        } else {
            response.setStatus(401);
            return null;
        }
    }
}