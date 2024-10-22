package com.paranmanzang.chatservice.controller;


import com.paranmanzang.chatservice.model.domain.message.ChatMessageModel;
import com.paranmanzang.chatservice.model.domain.message.RequestChatMessageModel;
import com.paranmanzang.chatservice.service.impl.ChatServiceImpl;
import com.paranmanzang.chatservice.util.ProfanityFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageHandler {

    private final ChatServiceImpl chatService;

    // SSE 메시지 구독 # 108
    public Mono<ServerResponse> findList(ServerRequest request) {
        return request.queryParam("nickname")
                .map(nickname ->
                        ServerResponse.ok()
                                .contentType(MediaType.TEXT_EVENT_STREAM)
                                .body(getSseMessageStream(request.pathVariable("roomId"), nickname), ServerSentEvent.class)
                )
                .orElseGet(() -> ServerResponse.badRequest().bodyValue(false));
    }

    // 실시간 및 과거 메시지 스트리밍
    private Flux<ServerSentEvent<ChatMessageModel>> getSseMessageStream(String roomId, String nickname) {
        return chatService.getMessageList(roomId, nickname)
                .map(chatMessage -> ServerSentEvent.<ChatMessageModel>builder()
                        .data(chatMessage)
                        .event("past-message")
                        .id(chatMessage.getRoomId())
                        .build())
                .concatWith(chatService.subscribeToRoom(roomId)
                        .map(message -> ServerSentEvent.<ChatMessageModel>builder()
                                .data(message)
                                .event("chat-message")
                                .id(roomId)
                                .build()));
    }

    // # 6
    public Mono<ServerResponse> insert(ServerRequest request) {
        return request.bodyToMono(RequestChatMessageModel.class)
                .flatMap(messageModel -> {

                    if (ProfanityFilter.containsProfanity(messageModel.getMessage())) {
                        messageModel.setMessage(ProfanityFilter.filterProfanity(messageModel.getMessage()));
                    }
                    return chatService.insertMessage(Mono.just(messageModel),  request.queryParam("nickname").orElseThrow())
                            .flatMap(isSuccess -> isSuccess
                                    ? ServerResponse.ok().bodyValue(true)
                                    : ServerResponse.ok().bodyValue(false));
                });
    }

    // # 109
    public Mono<ServerResponse> findUnReadTotalCount(ServerRequest request) {

        return chatService.totalUnReadMessageCount( request.queryParam("nickname").orElseThrow())
                .flatMap(totalCount -> ServerResponse.ok().bodyValue(totalCount));
    }

}
