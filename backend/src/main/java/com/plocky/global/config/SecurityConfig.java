package com.plocky.global.config;

import com.plocky.domain.member.repository.MemberRepository;
import com.plocky.global.jwt.CustomAuthenticationEntryPoint;
import com.plocky.global.jwt.filter.JwtExceptionFilter;
import com.plocky.global.jwt.filter.JwtRequestFilter;
import com.plocky.global.jwt.service.JwtService;

import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
//@AllArgsConstructor
@RequiredArgsConstructor
public class SecurityConfig extends SecurityConfigurerAdapter {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final String NO_CHECK_URL = "/login";


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(); // deprecated for spring security 7.0 (available for now)
        http.authorizeHttpRequests().anyRequest().permitAll();
        http.headers(headers -> headers.frameOptions().disable());
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter(), JwtRequestFilter.class);

        return http.build();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtService, memberRepository);
        return jwtRequestFilter;

    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        JwtExceptionFilter jwtExceptionFilter = new JwtExceptionFilter();
        return jwtExceptionFilter;
    }
}

