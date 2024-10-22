package com.paranmanzang.chatservice.model.repository;

import com.paranmanzang.chatservice.model.entity.ChatMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends ReactiveMongoRepository<ChatMessage, String>, CustomChatMessageRepository {
}
