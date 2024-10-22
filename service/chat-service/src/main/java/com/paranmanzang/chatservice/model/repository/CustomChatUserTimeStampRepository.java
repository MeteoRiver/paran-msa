package com.paranmanzang.chatservice.model.repository;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface CustomChatUserTimeStampRepository {

    Mono<LocalDateTime> findTimeByNicknameAndRoomId(String roomId, String nickname);

    Mono<Boolean> deleteByRoomId(String roomId);

    Mono<?> findByRoomIdAndNickname(String roomId, String nickname);

    Mono<?> updateReadLastTime (String roomId, String nickname);
}
