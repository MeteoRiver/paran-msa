package com.paranmanzang.chatservice.model.repository.impl;

import com.paranmanzang.chatservice.model.entity.ChatRoom;
import com.paranmanzang.chatservice.model.repository.CustomChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class CustomChatRoomRepositoryImpl implements CustomChatRoomRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Boolean> updateName(String roomId, String name) {
        return reactiveMongoTemplate.updateFirst(
                        Query.query(Criteria.where("id").is(roomId)),
                        new Update().set("name", name),
                        ChatRoom.class
                )
                .map(updateResult -> updateResult.getMatchedCount() > 0)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> updatePassword(String roomId,String password) {
        return reactiveMongoTemplate.updateFirst(
                        Query.query(Criteria.where("id").is(roomId)),
                        new Update().set("password", password),
                        ChatRoom.class
                )
                .map(updateResult -> updateResult.getMatchedCount() > 0)
                .onErrorReturn(false);
    }
}
