package com.plocky.domain.plogging.controller;

import com.plocky.domain.plogging.dto.CreatePloggingDto;
import com.plocky.domain.plogging.dto.ResponsePloggingDto;
import com.plocky.domain.plogging.service.PloggingService;
import com.plocky.global.jwt.service.JwtService;
import com.plocky.global.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PloggingController {
    private final PloggingService ploggingService;
    private final JwtService jwtService;

    @PostMapping("members/ploggings")
    public String createPlogging(HttpServletRequest request, HttpServletResponse response,
                                            @RequestBody CreatePloggingDto form) {
        String extractedKakaoId = jwtService.extractKakaoId(jwtService.extractAccessToken(request).orElseThrow()).orElseThrow();

        if (SecurityUtil.getLoginedUserName().equals(extractedKakaoId)) {
            String ploggingId = ploggingService.create(form, extractedKakaoId);
            response.setStatus(201);
            return ploggingId;
        } else {
            response.setStatus(401);
            return null;
        }
    }

    @GetMapping("members/ploggings/{ploggingId}")
    public ResponsePloggingDto getPlogging(HttpServletRequest request, HttpServletResponse response,
                                           @PathVariable("ploggingId") Long ploggingId) {
        String extractedKakaoId = jwtService.extractKakaoId(jwtService.extractAccessToken(request).orElseThrow()).orElseThrow();

        if (SecurityUtil.getLoginedUserName().equals(extractedKakaoId)) {
            ResponsePloggingDto responsePloggingDto = ploggingService.getPlogging(extractedKakaoId, ploggingId);
            response.setStatus(200);
            return responsePloggingDto;
        } else {
            response.setStatus(401);
            return null;
        }
    }
}
