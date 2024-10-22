package com.paranmanzang.gatewayserver.oauth;

import com.paranmanzang.gatewayserver.model.Domain.oauth.CustomUserDetails;
import com.paranmanzang.gatewayserver.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    public CustomReactiveUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return userRepository.findByUsername(username)
                .doOnNext(user -> log.info("Found user: {}", user)) // 로그 추가
                .map(user -> new CustomUserDetails(user));
    }
}
