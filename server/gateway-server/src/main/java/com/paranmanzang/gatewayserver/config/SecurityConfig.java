package com.paranmanzang.gatewayserver.config;

import com.paranmanzang.gatewayserver.Enum.Role;
import com.paranmanzang.gatewayserver.Filter.*;
import com.paranmanzang.gatewayserver.jwt.*;
import com.paranmanzang.gatewayserver.model.repository.UserRepository;
import com.paranmanzang.gatewayserver.oauth.CustomSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;
    private final CustomReactiveAuthenticationManager customReactiveAuthenticationManager;
    private final JWTUtil jwtUtil;
    private final JwtTokenServiceImpl jwtTokenService;

    public SecurityConfig(CustomAuthenticationSuccessHandler successHandler, CustomAuthenticationFailureHandler failureHandler, CustomReactiveAuthenticationManager customReactiveAuthenticationManager, CustomSuccessHandler customSuccessHandler, UserRepository userRepository, JWTUtil jwtUtil, JwtTokenServiceImpl jwtTokenService) {
        this.customSuccessHandler = customSuccessHandler;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.customReactiveAuthenticationManager = customReactiveAuthenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtTokenService = jwtTokenService;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                // CORS 설정
                .cors(corsCustomizer -> corsCustomizer
                        .configurationSource(request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000"));
                            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            configuration.setAllowCredentials(true);
                            configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "nickname"));
                            configuration.setMaxAge(3600L); // 1 hour
                            configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization", "nickname"));
                            return configuration;
                        }))
                // 기본 보안 설정 비활성화
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)

                // JWT 필터 추가
                .addFilterBefore(new JwtFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION)
                // OAuth2 설정
                .oauth2Login(oauth2 -> oauth2
                        .authenticationSuccessHandler(customSuccessHandler)
                )
                // 로그아웃 필터 추가
                .addFilterBefore(new LogoutFilter(jwtTokenService), SecurityWebFiltersOrder.AUTHENTICATION)
                // 로그인 필터 추가
                .addFilterAt(new LoginFilter(customReactiveAuthenticationManager, successHandler, failureHandler), SecurityWebFiltersOrder.AUTHENTICATION)
                // 닉네임 필터 추가 (JWT 필터 후에 실행)
                .addFilterAfter(new nicknameFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION)
                // Reissue 필터 추가
                .addFilterAfter(new ReissueFilter(jwtUtil, jwtTokenService), SecurityWebFiltersOrder.AUTHORIZATION)


                // 권한 설정
                .authorizeExchange(auth -> auth
                        // 인증이 필요 없는 경로 (모든 사용자 접근 가능)
                        .pathMatchers("/login", "/oauth2/**", "/api/users", "/api/users/checkPassword", "/api/users/checkNickname", "/api/users/checkRole", "/api/users/updateDeclaration", "/api/users/updateLogoutUserTime").permitAll()
                        .pathMatchers("/api/comments/{postId}").permitAll()
                        .pathMatchers("/api/rooms/enabled", "/api/rooms/enabled-all").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/files/*").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/files").permitAll()
                        .pathMatchers("/api/groups/books").permitAll()
                        .pathMatchers("/api/groups/groups").permitAll()
                        .pathMatchers("/api/groups/groups/users/{groupId}").permitAll()
                        .pathMatchers(HttpMethod.PUT, "/api/groups/posts/{postId}").permitAll()
                        .pathMatchers("/api/groups/posts/{groupId}").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/rooms/addresses", "/api/rooms/addresses/{query}", "/api/rooms/addresses/search").permitAll()
                        .pathMatchers("/api/rooms/reviews/room/{roomId}").permitAll()
                        .pathMatchers("/api/rooms/times/{roomId}").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/users/aboard", "/api/users/aboard/viewCounts/{id}", "/api/users/aboard/details/{id}").permitAll()

                        // 관리자 권한이 필요한 경로
                        .pathMatchers("/api/groups/groups/able", "/api/groups/groups/enable", "/api/groups/groups/enable-list").hasAuthority(Role.ROLE_ADMIN.getCode())
                        .pathMatchers("/api/rooms/confirm/{id}").hasAnyAuthority(Role.ROLE_ADMIN.getCode(), Role.ROLE_SELLER.getCode())
                        .pathMatchers(HttpMethod.DELETE, "/api/users/aboard/{id}").hasAuthority(Role.ROLE_ADMIN.getCode())
                        .pathMatchers(HttpMethod.PUT, "/api/users/aboard/{id}", "/api/users/updateRole").hasAuthority(Role.ROLE_ADMIN.getCode())
                        .pathMatchers(HttpMethod.POST, "/api/users/aboard").hasAuthority(Role.ROLE_ADMIN.getCode())
                        .pathMatchers(HttpMethod.GET, "/api/users/aboard/{nickname}", "/api/users/depost", "/api/users/findAllByNickname").hasAuthority(Role.ROLE_ADMIN.getCode())

                        // 판매자 권한이 필요한 경로
                        .pathMatchers("/api/rooms/accounts/room/{roomId}").hasAuthority(Role.ROLE_SELLER.getCode())
                        .pathMatchers(HttpMethod.POST, "/api/rooms/addresses").hasAuthority(Role.ROLE_SELLER.getCode())
                        .pathMatchers(HttpMethod.PUT, "/api/rooms/addresses").hasAuthority(Role.ROLE_SELLER.getCode())
                        .pathMatchers("/api/rooms/addresses/{id}").hasAuthority(Role.ROLE_SELLER.getCode())
                        .pathMatchers(HttpMethod.PUT, "/api/rooms/bookings/{id}").hasAuthority(Role.ROLE_SELLER.getCode())
                        .pathMatchers("/api/rooms/bookings/room/{roomId}").hasAuthority(Role.ROLE_SELLER.getCode())
                        .pathMatchers(HttpMethod.POST, "/api/rooms").hasAuthority(Role.ROLE_SELLER.getCode())
                        .pathMatchers(HttpMethod.PUT, "/api/rooms").hasAuthority(Role.ROLE_SELLER.getCode())
                        .pathMatchers("/api/rooms/{id}").hasAuthority(Role.ROLE_SELLER.getCode())
                        .pathMatchers("/api/rooms/user").hasAuthority(Role.ROLE_SELLER.getCode())

                        // 그 외 모든 경로는 인증 필요
                        .anyExchange().authenticated()
                );


        return http.build();
    }

    // Authorization 헤더에서 JWT 토큰을 추출하는 메서드
    private String resolveToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7).trim(); // Bearer 이후의 토큰만 반환
    }

}