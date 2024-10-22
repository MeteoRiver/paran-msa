package com.paranmanzang.chatservice.model.repository;

import reactor.core.publisher.Mono;

public interface CustomChatRoomRepository {

    Mono<Boolean> updateName(String roomId, String name);

    Mono<Boolean> updatePassword(String roomId, String password);

}
