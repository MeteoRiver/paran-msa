package com.paranmanzang.gatewayserver.jwt;


import com.paranmanzang.gatewayserver.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class JwtTokenServiceImpl {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final UserRepository userRepository;
    public JwtTokenServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void storeToken(String token, String nickname, long duration) {
        //redis에 jwt를 저장, 만료 시간도 설정
        redisTemplate.opsForValue().set(token, nickname, duration, TimeUnit.MILLISECONDS);
    }
    public Mono<Void> storeTokenM(String token, String nickname, long duration) {
        // Redis에 JWT를 저장하고, 만료 시간도 설정
        return Mono.fromRunnable(() ->
                redisTemplate.opsForValue().set(token, nickname, duration, TimeUnit.MILLISECONDS)
        );
    }

    public String getUserFromToken(String token) {
        //redis에서 JWT 조회
        return redisTemplate.opsForValue().get(token);
    }

    public void blacklistToken(String token, long expirationTime){
        //블랙리스트에 토큰 추가 => 해당 토큰은 접속이 차단 됨
        redisTemplate.opsForValue().set(token, "blacklist",expirationTime, TimeUnit.MILLISECONDS);
    }
    public boolean isTokenBlacklisted(String token){
        //토큰이 블랙리스트에 있는지 확인
        String result = redisTemplate.opsForValue().get(token);
        return result != null&&result.equals("blacklist");
    }

    public void deleteToken(String token) {
        //Redis에서 JWT 삭제 (로그아웃 시 사용)
        redisTemplate.delete(token);
    }
    public boolean isExist(String token) {
        if(redisTemplate.hasKey(token)){
            return true;
        }
        else{return false;}
    }
    public Mono<Boolean> isExistM(String token) {
        // Redis에서 키가 존재하는지 비동기로 확인
        return Mono.fromCallable(() -> redisTemplate.hasKey(token));
    }
}