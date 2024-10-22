package com.paranmanzang.chatservice.model.repository;

import com.paranmanzang.chatservice.model.entity.ChatRoom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends ReactiveMongoRepository<ChatRoom, String>, CustomChatRoomRepository {

}
