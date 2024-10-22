package com.paranmanzang.chatservice.model.repository.impl;
import com.paranmanzang.chatservice.model.entity.ChatUserTimeStamp;
import com.paranmanzang.chatservice.model.repository.CustomChatUserTimeStampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CustomChatUserTimeStampRepositoryImpl implements CustomChatUserTimeStampRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<LocalDateTime> findTimeByNicknameAndRoomId(String roomId, String nickname) {
        return reactiveMongoTemplate.findOne(
                Query.query(Criteria.where("roomId").is(roomId)
                        .and("nickname").is(nickname)), ChatUserTimeStamp.class)
                .map(ChatUserTimeStamp::getLastReadMessageTime);
    }

    @Override
    public Mono<Boolean> deleteByRoomId(String roomId) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("roomId").is(roomId)), ChatUserTimeStamp.class)
                .flatMap(deleteResult -> Mono.just(deleteResult.getDeletedCount() > 0))
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<?> findByRoomIdAndNickname(String roomId, String nickname) {
        return reactiveMongoTemplate.findOne(Query.query(Criteria.where("roomId").is(roomId)
                .and("nickname").is(nickname)),ChatUserTimeStamp.class);
    }

    @Override
    public Mono<Boolean> updateReadLastTime(String roomId, String nickname) {
        return reactiveMongoTemplate.updateFirst(
                        Query.query(Criteria.where("roomId").is(roomId)
                                .and("nickname").is(nickname)),
                        new Update().set("lastReadMessageTime", LocalDateTime.now()),
                        ChatUserTimeStamp.class)
                .map(updateResult -> updateResult.getModifiedCount() > 0)
                .defaultIfEmpty(false);
    }

}
