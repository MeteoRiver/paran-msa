package com.paranmanzang.chatservice.controller;

import com.paranmanzang.chatservice.service.impl.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ChatUserHandler {

    private final ChatServiceImpl chatService;

    // # 4
    public Mono<ServerResponse> insert(ServerRequest request) {
        return chatService.addUserToChatRoom(request.queryParam("roomId").orElse("defaultRoomId")
                        , request.queryParam("nickname").orElseThrow())
                .flatMap(success ->
                        success
                                ? ServerResponse.ok().bodyValue(true)
                                : ServerResponse.ok().bodyValue(false)
                );
    }

    // # 8
    public Mono<ServerResponse> findList(ServerRequest request) {
        return chatService.findNicknamesByRoomId(request.pathVariable("roomId"))
                .collectList()
                .flatMap(chatUsers -> ServerResponse.ok().bodyValue(chatUsers))
                .switchIfEmpty(ServerResponse.ok().bodyValue(false));
    }

    // # 9
    public Mono<ServerResponse> delete(ServerRequest request) {
        return chatService.exitRoom(request.pathVariable("roomId"),  request.queryParam("nickname").orElseThrow())
                .flatMap(success ->
                        success
                                ? ServerResponse.ok().bodyValue(true)
                                : ServerResponse.ok().bodyValue(false)
                );
    }
}
