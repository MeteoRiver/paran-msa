package com.paranmanzang.chatservice.service;


import com.paranmanzang.chatservice.model.domain.ChatUserModel;
import com.paranmanzang.chatservice.model.domain.message.ChatMessageModel;
import com.paranmanzang.chatservice.model.domain.message.RequestChatMessageModel;
import com.paranmanzang.chatservice.model.domain.room.ChatRoomModel;
import com.paranmanzang.chatservice.model.domain.room.ChatRoomNameModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ChatService {
    Mono<String> createRoom(Mono<ChatRoomNameModel> roomModel, String nickname);

    Flux<ChatRoomModel> getChatListByNickname(String nickname);

    Flux<ChatUserModel> findNicknamesByRoomId(String roomId);

    Mono<Boolean> addUserToChatRoom(String roomId, String nickname);

    Mono<Boolean> exitRoom(String roomId, String nickname);

    Mono<Boolean> deleteChatRoom(String roomId);

    Mono<Boolean> updateRoomName(String name, String roomId, String nickname);

    Mono<Boolean> updateRoomPassword(String password, String roomId, String nickname);

    Flux<ChatMessageModel> getMessageList(String roomId, String nickname);

    Mono<Boolean> insertMessage(Mono<RequestChatMessageModel> messageModel, String nickname);

    Mono<Boolean> insertReadLastTime(String roomId, String nickname);

    Mono<Integer> totalUnReadMessageCount(String nickname);

    Flux<ChatMessageModel> subscribeToRoom(String roomId);
}
