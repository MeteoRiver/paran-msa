package com.paranmanzang.chatservice.model.repository.impl;

import com.paranmanzang.chatservice.model.entity.ChatUser;
import com.paranmanzang.chatservice.model.repository.CustomChatUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class CustomChatUserRepositoryImpl implements CustomChatUserRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<ChatUser> findByNickname(String nickname) {
        return reactiveMongoTemplate.find(Query.query(Criteria.where("nickname").is(nickname)), ChatUser.class);
    }

    @Override
    public Mono<Integer> countByRoomId(String roomId) {
        return reactiveMongoTemplate.count(Query.query(Criteria.where("roomId").is(roomId)), ChatUser.class)
                .map(Long::intValue);
    }

    @Override
    public Flux<ChatUser> findByRoomId(String roomId) {
        return reactiveMongoTemplate.find(Query.query(Criteria.where("roomId").is(roomId)), ChatUser.class);
    }

    @Override
    public Mono<Boolean> deleteByRoomIdAndNickname(String roomId, String nickname) {
        return reactiveMongoTemplate.findAndRemove(Query.query(Criteria.where("roomId").is(roomId)
                        .and("nickname").is(nickname)), ChatUser.class)
                .flatMap(result -> Mono.just(result != null))
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<Boolean> deleteByRoomId(String roomId) {
        return findByRoomId(roomId)
                .flatMap(chatUser -> reactiveMongoTemplate.remove(
                        Query.query(Criteria.where("id").is(chatUser.getId())),
                        ChatUser.class)
                )
                .collectList()
                .flatMap(deleteResults ->
                        Mono.just(deleteResults.stream().allMatch(deleteResult -> deleteResult.getDeletedCount() > 0))
                )
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<ChatUser> findRoomOriginalCreator(String roomId) {
        return reactiveMongoTemplate.findOne(Query.query(Criteria.where("roomId").is(roomId))
                        .with(Sort.by(Sort.Direction.ASC, "enterTime"))
                        .limit(1), ChatUser.class);
    }

    @Override
    public Flux<String> findRoomIdByNickname(String nickname) {
        return reactiveMongoTemplate.find(Query.query(Criteria.where("nickname").is(nickname)), ChatUser.class)
                .flatMap(chatUser -> Flux.just(chatUser.getRoomId()));
    }
}
