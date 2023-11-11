package com.plocky.domain.auth.service;

import com.plocky.domain.auth.dto.KakaoInfoDto;
import com.plocky.domain.auth.dto.TokenDto;
import com.plocky.domain.auth.dto.TokenResponse;
import com.plocky.domain.member.repository.MemberRepository;
import com.plocky.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    @Value("${kakao.client.id}")
    private String REST_API_KEY;
    @Value("${kakao.client.redirect_uri}")
    private String REDIRECT_URI;
    @Value("${kakao.uri.authorization_uri}")
    private String AUTH_URI;
    @Value("${kakao.uri.token_uri}")
    private String TOKEN_URI;
    @Value("${kakao.uri.user-info-uri}")
    private String USER_URI;
    @Value("${kakao.uri.token-info-uri}")
    private String TOKEN_INFO_URI;

    // 로그인 요청 시 카카오 로그인 주소 반환
    public String login() {
        return AUTH_URI + "?client_id=" + REST_API_KEY + "&redirect_uri=" + REDIRECT_URI + "&response_type=code";
    }

    // 카카오 서버에 accessToken 요청
    private TokenResponse requestAccessToken(String auth_uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String requestBody = "{\"key\": \"value\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<TokenResponse> responseAccessEntity = restTemplate.exchange(auth_uri, HttpMethod.POST, requestEntity, TokenResponse.class);
        return responseAccessEntity.getBody();
    }

    // access token 이용해서 KakaoInfoDto 조회 후 반환
    public KakaoInfoDto checkIfAccessTokenIsValid(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<KakaoInfoDto> kakaoInfoDtoResponseEntity = restTemplate.exchange(TOKEN_INFO_URI, HttpMethod.GET, requestEntity, KakaoInfoDto.class);
        return kakaoInfoDtoResponseEntity.getBody();
    }

    // 인가코드 받아서 accessToken 발급
    public TokenDto access(String code) {
        String auth_uri = TOKEN_URI + "?grant_type=authorization_code&client_id=" + REST_API_KEY +
                "&redirect_uri=" + REDIRECT_URI + "&code=" + code;
        // access token 발급
        TokenResponse token = requestAccessToken(auth_uri);
        log.info("token = "+token.getAccessToken());

        // accessToken으로 카카오아이디 및 토큰 정보 조회
        KakaoInfoDto kakaoInfoDto = checkIfAccessTokenIsValid(token.getAccessToken());
        log.info("kakaoInfoDto = " + kakaoInfoDto);

        if (memberRepository.findByKakaoId(kakaoInfoDto.getKakaoId().toString()).orElse(null) == null){
            memberService.signup(kakaoInfoDto, token);
        }
        return TokenDto.builder().token(token.getAccessToken()).build();
    }
}
