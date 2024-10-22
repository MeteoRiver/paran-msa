package com.paranmanzang.chatservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranmanzang.chatservice.model.domain.ChatUserModel;
import com.paranmanzang.chatservice.model.domain.message.ChatMessageModel;
import com.paranmanzang.chatservice.model.domain.message.RequestChatMessageModel;
import com.paranmanzang.chatservice.model.domain.room.ChatRoomModel;
import com.paranmanzang.chatservice.model.domain.room.ChatRoomNameModel;
import com.paranmanzang.chatservice.model.entity.ChatMessage;
import com.paranmanzang.chatservice.model.entity.ChatRoom;
import com.paranmanzang.chatservice.model.entity.ChatUser;
import com.paranmanzang.chatservice.model.entity.ChatUserTimeStamp;
import com.paranmanzang.chatservice.model.eums.MessageType;
import com.paranmanzang.chatservice.model.repository.ChatMessageRepository;
import com.paranmanzang.chatservice.model.repository.ChatRoomRepository;
import com.paranmanzang.chatservice.model.repository.ChatUserRepository;
import com.paranmanzang.chatservice.model.repository.ChatUserTimeStampRepository;
import com.paranmanzang.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository roomRepository;
    private final ChatUserRepository userRepository;
    private final ChatMessageRepository messageRepository;
    private final ChatUserTimeStampRepository userTimeStampRepository;
    private final ReactiveRedisTemplate<String, ChatMessageModel> redisTemplate;
    private final ReactiveRedisMessageListenerContainer redisMessageListenerContainer;
    private final ObjectMapper objectMapper;


    @Override
    public Mono<String> createRoom(Mono<ChatRoomNameModel> roomModelMono, String nickname) {
        return roomModelMono
                .flatMap(roomModel -> {
                    return roomRepository.insert(ChatRoom.builder()
                                    .name(roomModel.getName())
                                    .createAt(LocalDateTime.now())
                                    .build())
                            .flatMap(insertedRoom -> addUserToRoomAndNotify(insertedRoom.getId(), nickname, MessageType.ENTER))
                            .flatMap(insertedRoom -> insertReadLastTime(insertedRoom.getId(), nickname)
                                    .thenReturn(insertedRoom.getId()));
                });
    }

    @Override
    public Mono<Boolean> addUserToChatRoom(String roomId, String nickname) {
        return roomRepository.findById(roomId)
                .flatMap(room ->
                        userRepository.insert(ChatUser.builder()
                                .roomId(roomId)
                                .nickname(nickname)
                                .enterTime(LocalDateTime.now())
                                .build()
                        ).flatMap(insertedUser ->
                                messageRepository.insert(ChatMessage.builder()
                                        .type(MessageType.ENTER)
                                        .roomId(roomId)
                                        .nickname(nickname)
                                        .message(nickname + MessageType.ENTER.getMessage())
                                        .createAt(LocalDateTime.now())
                                        .build()
                                ).flatMap(insertedMessage ->
                                        redisTemplate.convertAndSend("room:" + roomId, ChatMessageModel.builder()
                                                .type(insertedMessage.getType())
                                                .nickname(nickname)
                                                .message(insertedMessage.getMessage())
                                                .time(insertedMessage.getCreateAt())
                                                .roomId(insertedMessage.getRoomId())
                                                .build()
                                        )

                                ).then(Mono.just(true))
                        )
                ).onErrorResume(e -> Mono.just(false));
    }

    private Mono<ChatRoom> addUserToRoomAndNotify(String roomId, String nickname, MessageType messageType) {
        return userRepository.insert(ChatUser.builder()
                        .roomId(roomId)
                        .nickname(nickname)
                        .enterTime(LocalDateTime.now())
                        .build())
                .flatMap(insertedUser -> messageRepository.insert(ChatMessage.builder()
                                .type(messageType)
                                .roomId(roomId)
                                .nickname(nickname)
                                .message(nickname + messageType.getMessage())
                                .createAt(LocalDateTime.now())
                                .build())
                        .flatMap(insertedMessage -> notifyRoom(roomId, insertedMessage)))
                .then(roomRepository.findById(roomId));  // 방 정보 반환
    }

    // Redis로 알림을 보내는 로직을 별도로 분리
    private Mono<Long> notifyRoom(String roomId, ChatMessage insertedMessage) {
        return redisTemplate.convertAndSend("room:" + roomId, ChatMessageModel.builder()
                .type(insertedMessage.getType())
                .nickname(insertedMessage.getNickname())
                .message(insertedMessage.getMessage())
                .time(insertedMessage.getCreateAt())
                .roomId(insertedMessage.getRoomId())
                .build());
    }

    @Override
    public Flux<ChatRoomModel> getChatListByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .switchIfEmpty(Flux.defer(Flux::empty))
                .flatMap(chatUser -> roomRepository.findById(chatUser.getRoomId())
                        .switchIfEmpty(Mono.defer(Mono::empty))
                        .flatMap(chatRoom -> userRepository.countByRoomId(chatRoom.getId())
                                .switchIfEmpty(Mono.defer(Mono::empty))
                                .flatMap(userCount -> userTimeStampRepository.findTimeByNicknameAndRoomId(chatRoom.getId(), nickname)
                                        .switchIfEmpty(Mono.defer(Mono::empty))
                                        .flatMap(lastReadTime -> messageRepository.unReadMessageCountByRoomId(chatRoom.getId(), nickname, lastReadTime)
                                                .switchIfEmpty(Mono.defer(Mono::empty))
                                                .map(unReadMessageCount -> ChatRoomModel.builder()
                                                        .roomId(chatRoom.getId())
                                                        .name(chatRoom.getName())
                                                        .password(chatRoom.getPassword())
                                                        .userCount(userCount)
                                                        .unReadMessageCount(unReadMessageCount)
                                                        .build())))));
    }

    @Override
    public Flux<ChatUserModel> findNicknamesByRoomId(String roomId) {
        return userRepository.findByRoomId(roomId)
                .map(chatUser -> ChatUserModel.builder()
                        .nickname(chatUser.getNickname())
                        .enterTime(chatUser.getEnterTime())
                        .build());
    }

    @Override
    public Mono<Boolean> exitRoom(String roomId, String nickname) {
        return userRepository.deleteByRoomIdAndNickname(roomId, nickname)
                .flatMap(success -> messageRepository.insert(ChatMessage.builder()
                                .roomId(roomId)
                                .nickname(nickname)
                                .type(MessageType.EXIT)
                                .message(nickname + MessageType.EXIT.getMessage())
                                .createAt(LocalDateTime.now())
                                .build())
                        .flatMap(insertedMessage -> notifyRoom(roomId, insertedMessage))
                        .then(Mono.just(true)))
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public Mono<Boolean> deleteChatRoom(String roomId) {
        return roomRepository.deleteById(roomId)
                .then(userRepository.deleteByRoomId(roomId))
                .then(messageRepository.deleteByRoomId(roomId))
                .then(userTimeStampRepository.deleteByRoomId(roomId))
                .then(Mono.just(true))
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public Mono<Boolean> updateRoomName(String name, String roomId, String nickname) {
        return userRepository.findRoomOriginalCreator(roomId)
                .flatMap(user -> user.getNickname().equals(nickname)
                        ? roomRepository.updateName(roomId, name)
                        : Mono.just(false))
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public Mono<Boolean> updateRoomPassword(String password, String roomId, String nickname) {
        return userRepository.findRoomOriginalCreator(roomId)
                .flatMap(user -> user.getNickname().equals(nickname)
                        ? roomRepository.updatePassword(roomId, password)
                        : Mono.just(false))
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public Flux<ChatMessageModel> getMessageList(String roomId, String nickname) {
        return messageRepository.findByRoomId(roomId, nickname)
                .map(chatMessage -> ChatMessageModel.builder()
                        .id(chatMessage.getId())
                        .type(chatMessage.getType())
                        .message(chatMessage.getMessage())
                        .nickname(chatMessage.getNickname())
                        .time(chatMessage.getCreateAt())
                        .roomId(chatMessage.getRoomId())
                        .build());
    }

    @Override
    public Mono<Boolean> insertMessage(Mono<RequestChatMessageModel> messageModel, String nickname) {
        return messageModel
                .flatMap(message -> messageRepository.insert(ChatMessage.builder()
                                .type(message.getType())
                                .nickname(nickname)
                                .message(message.getMessage())
                                .createAt(LocalDateTime.now())
                                .roomId(message.getRoomId())
                                .build())
                        .flatMap(savedMessage -> notifyRoom(savedMessage.getRoomId(), savedMessage))
                        .then(Mono.just(true)))
                .onErrorResume(e -> Mono.just(false));
    }

    @Override
    public Flux<ChatMessageModel> subscribeToRoom(String roomId) {
        return redisMessageListenerContainer.receive(new ChannelTopic("room:" + roomId))
                .mapNotNull(redisMessage -> {
                    var messageBody = (String) redisMessage.getMessage();
                    try {
                        return objectMapper.readValue(messageBody, ChatMessageModel.class);
                    } catch (Exception e) {
                        log.error("Failed to deserialize message: {}", messageBody, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull);
    }

    @Override
    public Mono<Boolean> insertReadLastTime(String roomId, String nickname) {
        return userTimeStampRepository.findByRoomIdAndNickname(roomId, nickname)
                .flatMap(findOne -> {
                    return userTimeStampRepository.updateReadLastTime(roomId, nickname)
                            .then(Mono.just(true));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    return userTimeStampRepository.save(ChatUserTimeStamp.builder()
                                    .roomId(roomId)
                                    .nickname(nickname)
                                    .lastReadMessageTime(LocalDateTime.now())
                                    .build())
                            .then(Mono.just(true));
                }))
                .onErrorResume(e -> {
                    return Mono.just(false);
                });
    }

    @Override
    public Mono<Integer> totalUnReadMessageCount(String nickname) {
        return userRepository.findRoomIdByNickname(nickname)
                .flatMap(roomId -> userTimeStampRepository.findTimeByNicknameAndRoomId(roomId, nickname)
                        .flatMap(lastReadTime -> messageRepository.unReadMessageCountByRoomId(roomId, nickname, lastReadTime)))
                .reduce(0, Integer::sum);
    }
}
