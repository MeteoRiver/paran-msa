package com.paranmanzang.chatservice.model.repository;

import com.paranmanzang.chatservice.model.entity.ChatUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends ReactiveMongoRepository<ChatUser, String>, CustomChatUserRepository {
}
