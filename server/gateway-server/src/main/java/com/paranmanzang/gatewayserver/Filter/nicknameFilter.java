package com.paranmanzang.gatewayserver.Filter;

import com.paranmanzang.gatewayserver.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class nicknameFilter implements WebFilter {
    private final JWTUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().equals("/findNicknameByToken")) {
            // Refresh Token 검증
            String refreshToken = exchange.getRequest().getCookies().getFirst("refresh") != null
                    ? exchange.getRequest().getCookies().getFirst("refresh").getValue()
                    : null;
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String accessToken = authHeader;
/*            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                accessToken = authHeader.substring(7); // "Bearer " 이후의 토큰 추출
                log.info("Access token: {}", accessToken);
            }*/

            if (refreshToken == null) {
                log.warn("Refresh token is missing");
                return chain.filter(exchange); // Refresh Token이 없으면 다음 필터로 진행
            }

            String nickname = jwtUtil.getNickNameFromToken(accessToken); // accessToken 사용하여 nickname 얻기

            if (nickname == null) {
                log.warn("Failed to retrieve username from token");
                return chain.filter(exchange); // nickname이 없으면 다음 필터로 진행
            }

            log.info("Username retrieved from token: {}", nickname);
            exchange.getResponse().getHeaders().add("Authorization", "Bearer " + accessToken);
            exchange.getResponse().getHeaders().add("nickname", nickname+"1");

            exchange.getResponse().addCookie(createCookie("refresh", refreshToken));
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }
    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.fromClientResponse(key, value)
                .maxAge(86400)  // 쿠키의 만료 시간을 초 단위로 설정
                .path("/")
                .httpOnly(true)
                .build();
    }
}