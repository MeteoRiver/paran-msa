package com.paranmanzang.gatewayserver.Filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranmanzang.gatewayserver.jwt.CustomAuthenticationFailureHandler;
import com.paranmanzang.gatewayserver.jwt.CustomAuthenticationSuccessHandler;
import com.paranmanzang.gatewayserver.jwt.CustomReactiveAuthenticationManager;
import com.paranmanzang.gatewayserver.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class LoginFilter extends AuthenticationWebFilter {
    private final CustomReactiveAuthenticationManager authenticationManager;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;

    public LoginFilter(CustomReactiveAuthenticationManager authenticationManager, CustomAuthenticationSuccessHandler successHandler, CustomAuthenticationFailureHandler failureHandler) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    private Mono<Void> convert(ServerWebExchange exchange, WebFilterChain webFilterChain) {
        return exchange.getRequest().getBody()
                .next()
                .flatMap(dataBuffer -> {
                    String requestBody = dataBuffer.toString(StandardCharsets.UTF_8);
                    log.info("Request body: {}", requestBody);

                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map<String, Object> loginData = objectMapper.readValue(requestBody, new TypeReference<Map<String, Object>>() {});
                        String username = extractValueFromLoginData(loginData.get("username"));
                        String password = extractValueFromLoginData(loginData.get("password"));

                        log.info("Received username: {}, password: [PROTECTED]", username);

                        if (username == null || password == null) {
                            log.error("Invalid login attempt: Missing username or password");
                            return Mono.error(new BadCredentialsException("Invalid username or password"));
                        }

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
                        log.info("Authentication token created for user: {}", username);

                        // 인증 처리
                        return authenticationManager.authenticate(authToken)
                                .flatMap(authentication -> {
                                    log.info("Authentication success for user: {}", authentication.getName());


                                    // JWT 생성 및 응답 처리 후, 리다이렉션 처리
                                    return successHandler.handleSuccess(exchange, authentication);
                                })
                                .onErrorResume(e -> {
                                    log.error("Authentication failed: {}", e.getMessage());
                                    return failureHandler.handleFailure(exchange, e);
                                });

                    } catch (IOException e) {
                        log.error("Error parsing request body: {}", e.getMessage());
                        return Mono.error(new IllegalArgumentException("Invalid login data"));
                    }
                });
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().equals("/login")) {
            log.info("Handling login request for path: {}", exchange.getRequest().getURI().getPath());
            return convert(exchange, chain);
        }
        // 로그인 요청이 아닌 경우 필터 체인 계속 진행
        return chain.filter(exchange);
    }

    private String extractValueFromLoginData(Object obj) {
        if (obj instanceof Map) {
            return (String) ((Map<?, ?>) obj).get("value");
        }
        return obj instanceof String ? (String) obj : null;
    }
}
