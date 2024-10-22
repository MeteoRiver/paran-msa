package com.paranmanzang.gatewayserver.controller;

import com.paranmanzang.gatewayserver.Enum.Role;
import com.paranmanzang.gatewayserver.model.RegisterModel;
import com.paranmanzang.gatewayserver.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final Validator validator;

    public Mono<ServerResponse> insert(ServerRequest request) {
        return request.bodyToMono(RegisterModel.class)
                .doOnNext(userModel -> {
                    var errors = new BeanPropertyBindingResult(userModel, RegisterModel.class.getName());
                    validator.validate(userModel, errors);

                    if (errors.hasErrors()) {
                        throw new IllegalArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
                    }
                })
                .flatMap(model -> userService.insert(model)
                        .flatMap(result -> ServerResponse.ok().bodyValue(true)))
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.ok().bodyValue(false))
                .onErrorResume(e ->
                        ServerResponse.ok().bodyValue(false));

    }
    public Mono<ServerResponse> remove(ServerRequest request) {
        String nickname = request.queryParam("nickname")
                .orElseThrow(() -> new IllegalArgumentException("닉네임이 필요합니다"));
        log.info("nickname: " + nickname);
        return userService.remove(nickname)
                .flatMap(success -> {
                    if (success) {
                        return ServerResponse.ok().bodyValue(true);
                    } else {
                        return ServerResponse.ok().bodyValue(false);
                    }
                })
                .onErrorResume(e -> {
                    // 예외 발생 시 500 Internal Server Error 응답 반환
                    return ServerResponse.ok().bodyValue(false);
                });
    }

    public Mono<ServerResponse> updatePassword(ServerRequest request) {
        String nickname = request.queryParam("nickname")
                .orElseThrow(() -> new IllegalArgumentException("닉네임이 필요합니다"));
        String newPassword = request.queryParam("newPassword")
                .orElseThrow(() -> new IllegalArgumentException("새 비밀번호가 필요합니다"));

        return userService.updatePassword(nickname, newPassword)
                .flatMap(success -> ServerResponse.ok().bodyValue(true)
                        .onErrorResume(IllegalArgumentException.class, e ->
                                ServerResponse.ok().bodyValue(false))
                        .onErrorResume(e ->
                                ServerResponse.ok().bodyValue(false)));
    }


    public Mono<ServerResponse> updateDeclaration(ServerRequest request) {
        String nickname = request.queryParam("nickname")
                .orElseThrow(() -> new IllegalArgumentException("닉네임이 필요합니다"));
        return userService.updateDeclaration(nickname)
                .flatMap(success -> ServerResponse.ok().bodyValue(true))
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.ok().bodyValue(false))
                .onErrorResume(e ->
                        ServerResponse.ok().bodyValue(false));
    }


    public Mono<ServerResponse> findAllByNickname(ServerRequest request) {
        String nickname = request.queryParam("nickname")
                .orElseThrow(() -> new IllegalArgumentException("관리자 닉네임이 필요합니다"));

        return userService.findAllByNickname(nickname)
                .flatMap(users -> ServerResponse.ok().bodyValue(users))
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.ok().bodyValue(false))
                .onErrorResume(e ->
                        ServerResponse.ok().bodyValue(false));
    }

    public Mono<ServerResponse> findByNickname(ServerRequest request) {
        String nickname = request.queryParam("nickname")
                .orElseThrow(() -> new IllegalArgumentException("닉네임이 필요합니다"));

        return userService.findByNickname(nickname)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.notFound().build())
                .onErrorResume(e ->
                        ServerResponse.ok().bodyValue(false));
    }
    public Mono<ServerResponse> checkNickname(ServerRequest request) {
        return request.bodyToMono(RegisterModel.class)
                .flatMap(registerModel -> userService.checkNickname(registerModel)
                        .flatMap(isAvailable -> {
                            if (isAvailable) {
                                return ServerResponse.ok().bodyValue(true);
                            } else {
                                return ServerResponse.ok().bodyValue(false);
                            }
                        })
                )
                .onErrorResume(DataAccessException.class, e ->
                        ServerResponse.ok().bodyValue(false))
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.ok().bodyValue(false));
    }
    public Mono<ServerResponse> checkUsername(ServerRequest request) {
        return request.bodyToMono(RegisterModel.class)
                .flatMap(registerModel -> userService.checkUsername(registerModel)
                        .flatMap(isAvailable -> {
                            if (isAvailable) {
                                return ServerResponse.ok().bodyValue(true);
                            } else {
                                return ServerResponse.ok().bodyValue(false);
                            }
                        })
                )
                .onErrorResume(DataAccessException.class, e ->
                        ServerResponse.ok().bodyValue(false))
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.ok().bodyValue(false));
    }

    public Mono<ServerResponse> checkPassword(ServerRequest request) {
        return request.bodyToMono(RegisterModel.class)
                .flatMap(registerModel -> {
                    boolean isPasswordValid = userService.checkPassword(registerModel).block(); // 비밀번호 체크
                    if (isPasswordValid) {
                        return ServerResponse.ok().bodyValue(true);
                    } else {
                        return ServerResponse.ok().bodyValue(false);
                    }
                })
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.ok().bodyValue(false));
    }
    public Mono<ServerResponse> updateLogoutUserTime(ServerRequest request) {
        String nickname = request.queryParam("nickname")
                .orElseThrow(() -> new IllegalArgumentException("닉네임이 필요합니다."));

        log.info("로그아웃 요청: 닉네임 {}", nickname);

        return userService.updateLogoutTime(nickname)
                .flatMap(success -> {
                    if (success) {
                        return ServerResponse.ok().bodyValue(true);
                    } else {
                        return ServerResponse.ok().bodyValue(false);
                    }
                })
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.badRequest().bodyValue(true))
                .onErrorResume(e ->
                        ServerResponse.ok().bodyValue(false));
    }

    public Mono<ServerResponse> updateRole(ServerRequest request) {
        String nickname = request.queryParam("nickname")
                .orElseThrow(() -> new IllegalArgumentException("닉네임이 필요합니다"));
        Role newRole = Role.valueOf(request.queryParam("newRole")
                .orElseThrow(() -> new IllegalArgumentException("새로운 권한이 필요합니다")));

        return userService.updateRole(nickname, newRole)
                .flatMap(success -> {
                    if (success) {
                        return ServerResponse.ok().bodyValue(true);
                    } else {
                        return ServerResponse.ok().bodyValue(false);
                    }
                })
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.ok().bodyValue(false))
                .onErrorResume(e ->
                        ServerResponse.ok().bodyValue(false));
    }

    public Mono<ServerResponse> checkRole(ServerRequest request){
        String nickname = request.queryParam("nickname")
                .orElseThrow(() -> new IllegalArgumentException("닉네임이 필요합니다"));
        return userService.checkRole(nickname)
                .flatMap(role -> ServerResponse.ok().bodyValue(role))
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.ok().bodyValue(false)) // 잘못된 요청(닉네임이 잘못되거나 사용자 없음)
                .onErrorResume(e ->
                        ServerResponse.ok().bodyValue(false)); // 그 외 오류
    }
}
