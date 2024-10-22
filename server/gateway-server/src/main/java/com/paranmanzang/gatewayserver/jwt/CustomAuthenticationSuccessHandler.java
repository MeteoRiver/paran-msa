package com.paranmanzang.gatewayserver.jwt;

import com.paranmanzang.gatewayserver.model.Domain.oauth.CustomUserDetails;
import com.paranmanzang.gatewayserver.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final JwtTokenServiceImpl jwtTokenService;

    public CustomAuthenticationSuccessHandler(JWTUtil jwtUtil, JwtTokenServiceImpl jwtTokenService) {
        this.jwtUtil = jwtUtil;
        this.jwtTokenService = jwtTokenService;
    }

    public Mono<Void> handleSuccess(ServerWebExchange exchange, Authentication authentication) {
        log.info("authentication : {}", authentication.getPrincipal());
        User user = (User) authentication.getPrincipal();
        CustomUserDetails userDetails = new CustomUserDetails(user); // User를 기반으로 CustomUserDetails 생성
        log.info("userDetails: {}", userDetails);

        String username = userDetails.getUsername();
        String nickname = userDetails.getNickname();
        log.info("username : {}", username);
        log.info("nickname : {}", nickname);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();
        log.info("role : {}", role);

        String access = jwtUtil.createAccessJwt(username, role, nickname, 600000L);
        log.info("access : {}", access);

        String refresh = jwtUtil.createRefreshJwt(username, role, nickname, 86400000L);
        log.info("refresh : {}", refresh);

        jwtTokenService.storeToken(refresh, username, 86400000L);

        // Authorization 헤더에 accessToken 추가
        exchange.getResponse().getHeaders().add("Authorization", "Bearer " + access);
        exchange.getResponse().getHeaders().add("nickname", nickname);

        // RefreshToken을 쿠키에 추가
        exchange.getResponse().addCookie(createCookie("refresh", refresh));
        //exchange.getResponse().getHeaders().add(HttpHeaders.SET_COOKIE, "refresh=" + refresh + "; Path=/; HttpOnly");

        log.info("Response Cookies: {}", exchange.getResponse().getCookies());

        // 응답 완료
        return exchange.getResponse().setComplete()
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                .doOnSuccess(unused -> {
                    log.info("User '{}' has been authenticated and JWT tokens returned", username);
                })
                .doOnError(error -> {
                    log.error("Failed to authenticate user '{}': {}", username, error.getMessage());
                });
    }

    // 리프레시 토큰을 위한 쿠키 생성 메서드
    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .maxAge(86400)
                .path("/")
                .httpOnly(false)
                .build();
    }
}

