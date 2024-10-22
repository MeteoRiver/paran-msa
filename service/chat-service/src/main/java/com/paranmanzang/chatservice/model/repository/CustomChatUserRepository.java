package com.paranmanzang.chatservice.model.repository;

import com.paranmanzang.chatservice.model.entity.ChatUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomChatUserRepository {

    Flux<ChatUser> findByNickname(String nickname);

    Mono<Integer> countByRoomId(String roomId);

    Flux<ChatUser> findByRoomId(String roomId);

    Mono<Boolean> deleteByRoomIdAndNickname(String roomId, String nickname);

    Mono<Boolean> deleteByRoomId(String roomId);

    Mono<ChatUser> findRoomOriginalCreator(String roomId);

    Flux<String> findRoomIdByNickname(String nickname);
}
