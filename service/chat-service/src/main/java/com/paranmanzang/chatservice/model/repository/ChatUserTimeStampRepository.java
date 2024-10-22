package com.paranmanzang.chatservice.model.repository;

import com.paranmanzang.chatservice.model.entity.ChatUserTimeStamp;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserTimeStampRepository extends ReactiveMongoRepository<ChatUserTimeStamp, String>, CustomChatUserTimeStampRepository {
}
