package com.paranmanzang.gatewayserver.jwt;

import com.paranmanzang.gatewayserver.model.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTUtil {

    private final SecretKey secretKey;
    private final SecretKey refreshSecretKey;
    private final UserRepository userRepository;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret, @Value("${spring.jwt.refreshSecret}") String refreshSecret, UserRepository userRepository) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.refreshSecretKey = new SecretKeySpec(refreshSecret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.userRepository = userRepository;
    }
    //access토큰 발급(get용) claim없는 토큰
    //public Mono<Claims> parseToken(String token) {}

    //access토큰 발급(Post용)
    public String createAccessJwt(String username, String role, String nickname, Long expiredMs) {
        return Jwts.builder()
                .claim("category", "access")
                .claim("username", username)
                .claim("nickname", nickname)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    //refresh토큰 발급(Post용)
    public String createRefreshJwt(String username, String role, String nickname, Long expiredMs) {
        return Jwts.builder()
                .claim("category", "refresh")
                .claim("username", username)
                .claim("nickname", nickname)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(refreshSecretKey)
                .compact();
    }

    // Access 토큰의 만료 여부를 확인
    public Boolean isExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration().before(new Date());
    }

    // Access토큰의 특정 클레임의 값을 반환
    public String getClaim(String token, String claimName) {
        Claims claims = getClaimsFromToken(token);
        return claims.get(claimName, String.class);
    }

    // Access토큰에서 모든 클레임을 추출
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    // Refresh 토큰의 만료 여부를 확인
    public Boolean isExpiredR(String token) {
        Claims claims = getRefreshClaimsFromToken(token);
        return claims.getExpiration().before(new Date());
    }

    public Mono<Authentication> getAuthentication(String token) {
        String username = getUsernameFromToken(token);

        if (username != null) {
            return userRepository.findByUsername(username)
                    .flatMap(user -> {
                        // 사용자의 권한을 가져옵니다.
                        Collection<GrantedAuthority> authorities = Arrays.stream(user.getRole().values()) // Enum의 모든 값을 스트림으로 변환
                                .map(role -> new SimpleGrantedAuthority(role.name())) // GrantedAuthority로 변환
                                .collect(Collectors.toList()); // List로 수집
                        log.info("authorities :{}", authorities);
                        log.info("authorities2 :{}", Mono.just(new UsernamePasswordAuthenticationToken(user, null, authorities)));

                        // Authentication 객체를 반환합니다.
                        return Mono.just(new UsernamePasswordAuthenticationToken(user, null, authorities));
                    });
        }
        return Mono.empty(); // username이 null인 경우 빈 Mono를 반환
    }

    // Refresh토큰의 특정 클레임의 값을 반환
    public String getClaimR(String token, String claimName) {
        Claims claims = getRefreshClaimsFromToken(token);
        return claims.get(claimName, String.class);
    }
    // Refresh토큰에서 모든 클레임을 추출
    private Claims getRefreshClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(refreshSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Access 토큰에서 닉네임 클레임을 추출
    public String getNickNameFromToken(String token) {
        return getClaim(token, "nickname");
    }
    // Access 토큰에서 Role 클레임을 추출
    public List<String> getRoleFromToken(String token) {
        String role = getClaim(token, "role");
        return List.of(role.split(",")); // 역할을 콤마로 구분된 문자열로 가정
    }
    // Access 토큰에서 이메일 클레임을 추출
    public String getUsernameFromToken(String token) {
        return getClaim(token, "username");
    }
    // Access 토큰에서 카테고리 클레임을 추출
    public String getCategoryFromToken(String token) {
        return getClaim(token, "category");
    }
    //
    // Refresh 토큰에서 닉네임 클레임을 추출
    public String getNickNameFromTokenR(String token) {
        return getClaimR(token, "nickname");
    }
    // Refresh 토큰에서 Role 클레임을 추출
    public List<String> getRoleFromTokenR(String token) {
        String role = getClaimR(token, "role");
        return List.of(role.split(",")); // 역할을 콤마로 구분된 문자열로 가정
    }
    // Refresh 토큰에서 이메일 클레임을 추출
    public String getUsernameFromTokenR(String token) {
        return getClaimR(token, "username");
    }
    // Refresh 토큰에서 카테고리 클레임을 추출
    public String getCategoryFromTokenR(String token) {
        return getClaimR(token, "category");
    }

}


