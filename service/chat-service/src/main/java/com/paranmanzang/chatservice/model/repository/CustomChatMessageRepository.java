package com.paranmanzang.chatservice.model.repository;

import com.paranmanzang.chatservice.model.entity.ChatMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface CustomChatMessageRepository {
    Mono<Boolean> deleteByRoomId(String roomId);

    Flux<ChatMessage> findByRoomId(String roomId, String nickname);

    Mono<Integer> unReadMessageCountByRoomId(String roomId, String nickname, LocalDateTime lastReadTime);

}
