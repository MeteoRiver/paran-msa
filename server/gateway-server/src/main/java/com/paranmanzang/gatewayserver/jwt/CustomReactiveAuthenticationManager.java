package com.paranmanzang.gatewayserver.jwt;

import com.paranmanzang.gatewayserver.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomReactiveAuthenticationManager(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }



    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        log.info("여기부터 커스텀 리액티브 어쎈틱케이션매니저 Authenticating user {} with password {}", username, password);

        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new BadCredentialsException("User not found")))
                .flatMap(user -> {
                    log.info("userPassword:{}", user.getPassword());
                    log.info("userUsername:{}", user.getUsername());
                    log.info("탈퇴여부: {}", user.isState());
                    if (!user.isState()) {
                        return Mono.error(new BadCredentialsException("User not found"));
                    }
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        log.info("여기까지 커스텀 리액티브 어쎈틱케이션매니저 passwordEncoder.encode(password)1 : {}", passwordEncoder.encode(password));
                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getCode()); // Role에서 권한 이름 가져오기
                        return Mono.just(new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(authority)));
                    } else {
                        log.info("여기까지 커스텀 리액티브 어쎈틱케이션매니저 passwordEncoder.encode(password)2 : {}", passwordEncoder.encode(password));
                        return Mono.error(new BadCredentialsException("Invalid credentials"));
                    }

                });

    }
}