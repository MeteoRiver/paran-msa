package com.paranmanzang.gatewayserver.oauth;

import com.paranmanzang.gatewayserver.model.Domain.oauth.CustomOAuth2User;
import com.paranmanzang.gatewayserver.jwt.JWTUtil;
import com.paranmanzang.gatewayserver.jwt.JwtTokenServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final JwtTokenServiceImpl jwtTokenService;

    public CustomSuccessHandler(JWTUtil jwtUtil, JwtTokenServiceImpl jwtTokenService) {
        this.jwtUtil = jwtUtil;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange exchange, Authentication authentication) {
        ServerWebExchange serverWebExchange = exchange.getExchange();
        ServerHttpResponse response = serverWebExchange.getResponse();

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getUsername();
        String nickname = customUserDetails.getNickname();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        GrantedAuthority auth = authorities.iterator().next();
        String role = auth.getAuthority();
        String access = jwtUtil.createAccessJwt(username, role, nickname, 3600000L);
        String refresh = jwtUtil.createRefreshJwt(username, role, nickname, 86400000L);
        response.getHeaders().set("Authorization", "Bearer " + access);
        response.getHeaders().set("nickname", nickname);
        response.addCookie(createCookie("refresh", refresh)); //paranmanzang.com
        jwtTokenService.storeToken(refresh, nickname, 86400000L);

        String returnUrl = serverWebExchange.getRequest().getQueryParams().getFirst("return_url");
        if (returnUrl != null) {
            response.getHeaders().setLocation(URI.create(returnUrl));
        } else {
            response.getHeaders().setLocation(URI.create("http://localhost:3000")); // 기본값
        }

        response.setStatusCode(HttpStatus.FOUND); // 302 Found로 변경
        //response.setStatusCode(HttpStatus.OK);
        return response.setComplete();
    }

    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.fromClientResponse(key, value)
                .maxAge(86400)  // 쿠키의 만료 시간을 초 단위로 설정
                .path("/")
                .httpOnly(false)
                .build();
    }
}