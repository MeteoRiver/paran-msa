package com.paranmanzang.gatewayserver.Filter;

import com.paranmanzang.gatewayserver.jwt.JWTUtil;
import com.paranmanzang.gatewayserver.jwt.JwtTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.http.ResponseCookie;

@Slf4j
@RequiredArgsConstructor
public class ReissueFilter implements WebFilter {
    private final JWTUtil jwtUtil;
    private final JwtTokenServiceImpl jwtTokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().equals("/reissue")) {
            // Refresh Token 검증
            String refreshToken = exchange.getRequest().getCookies().getFirst("refresh") != null
                    ? exchange.getRequest().getCookies().getFirst("refresh").getValue()
                    : null;

            if (refreshToken == null) {
                log.warn("Refresh token is missing");
                return chain.filter(exchange); // Refresh Token이 없으면 다음 필터로 진행
            }

            log.info("Reissue request received with refresh token: {}", refreshToken);

            // Refresh Token 검증 및 재발급 로직
            return reissue(exchange, refreshToken)
                    .doOnSuccess(responseEntity -> {
                        // 재발급 로직이 성공했을 때 추가 작업을 여기서 할 수 있습니다.
                        log.info("Token successfully reissued.");
                    })
                    .doOnError(e -> {
                        log.error("Error during token reissue: {}", e.getMessage());
                        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(("Token reissue error: " + e.getMessage()).getBytes());
                        exchange.getResponse().writeWith(Mono.just(buffer)).subscribe(); // 오류 메시지 전송
                    })
                    .then(chain.filter(exchange)); // 다음 필터로 넘어감
        }
        return chain.filter(exchange);
    }

    private Mono<ResponseEntity<?>> reissue(ServerWebExchange exchange, String refreshToken) {
        log.info("Reissue token logic started");

        // Refresh 토큰이 유효한지 검증
        return jwtTokenService.isExistM(refreshToken)
                .flatMap(isValid -> {
                    if (isValid) {
                        log.info("레디스랑 쿠키 비교 완료");
                        // JWT 토큰 검증 및 정보 추출
                        String usernameFromR = jwtUtil.getUsernameFromTokenR(refreshToken);
                        String userRoleFromR = jwtUtil.getRoleFromTokenR(refreshToken).get(0);  // 첫 번째 역할만 가져오거나 적절히 처리
                        String nicknameFromR = jwtUtil.getNickNameFromTokenR(refreshToken);

                        log.info("usernameFromR : {}", usernameFromR);
                        log.info("userRoleFromR : {}", userRoleFromR);
                        log.info("nicknameFromR : {}", nicknameFromR);

                        jwtTokenService.deleteToken(refreshToken);

                        // 새로운 Access/Refresh 토큰 생성
                        String newAccessToken = jwtUtil.createAccessJwt(usernameFromR, userRoleFromR, nicknameFromR, 600000L);
                        String newRefreshToken = jwtUtil.createRefreshJwt(usernameFromR, userRoleFromR, nicknameFromR, 86400000L);

                        log.info("newAccessToken : {}", newAccessToken);
                        log.info("newRefreshToken : {}", newRefreshToken);

                        exchange.getResponse().getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
                        exchange.getResponse().addCookie(createCookie("refresh", newRefreshToken));

                        // 새로운 Refresh 토큰 저장
                        return jwtUtil.getAuthentication(newAccessToken)
                                .flatMap(authentication -> {
                                    // Authentication 객체를 활용하여 추가 로직을 수행합니다.
                                    return jwtTokenService.storeTokenM(newRefreshToken, nicknameFromR, 86400000L)
                                            .then(Mono.just(ResponseEntity.ok("Token reissued successfully")))
                                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                                })
                                .switchIfEmpty(Mono.error(new RuntimeException("유효한 사용자 정보가 없습니다.")));
                    }  else {
                        return Mono.error(new RuntimeException("재로그인을 진행해주세요"));
                    }
                });
    }

    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .maxAge(86400)
                .path("/")
                .httpOnly(false)
                .build();
    }
}