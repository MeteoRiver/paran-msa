package com.paranmanzang.chatservice.model.repository.impl;

import com.paranmanzang.chatservice.model.entity.ChatMessage;
import com.paranmanzang.chatservice.model.repository.CustomChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@RequiredArgsConstructor
public class CustomChatMessageRepositoryImpl implements CustomChatMessageRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Boolean> deleteByRoomId(String roomId) {
        return reactiveMongoTemplate.find(Query.query(Criteria.where("roomId").is(roomId)), ChatMessage.class)
                .flatMap(chatMessage -> reactiveMongoTemplate
                        .remove(Query.query(Criteria.where("id").is(chatMessage.getId())), ChatMessage.class)
                )
                .collectList()
                .flatMap(deleteResults ->
                        Mono.just(deleteResults.stream().allMatch(deleteResult -> deleteResult.getDeletedCount() > 0))
                )
                .defaultIfEmpty(false);
    }

    @Tailable
    @Override
    public Flux<ChatMessage> findByRoomId(String roomId, String nickname) {
        return getEnterRoomTime(nickname, roomId)
                .flatMapMany(enterTime ->
                        reactiveMongoTemplate.find(
                                Query.query(Criteria.where("roomId").is(roomId)
                                                .and("createAt").gte(enterTime))
                                        .with(Sort.by(Sort.Direction.ASC, "createAt")),
                                ChatMessage.class
                        )
                );
    }


    private Mono<LocalDateTime> getEnterRoomTime(String nickname, String roomId) {
        return reactiveMongoTemplate.findOne(
                Query.query(Criteria.where("roomId").is(roomId)
                                .and("type").is("ENTER")
                                .and("nickname").is(nickname))
                        .limit(1),
                ChatMessage.class
        ).map(ChatMessage::getCreateAt);
    }

    @Override
    public Mono<Integer> unReadMessageCountByRoomId(String roomId, String nickname, LocalDateTime lastReadTime) {
        return reactiveMongoTemplate.count(
                Query.query(Criteria.where("roomId").is(roomId)
                        .and("createAt").gte(lastReadTime)), ChatMessage.class)
                .map(Long::intValue);
    }

}
