package com.paranmanzang.chatservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranmanzang.chatservice.model.domain.room.ChatRoomNameModel;
import com.paranmanzang.chatservice.model.domain.room.ChatRoomPasswordModel;
import com.paranmanzang.chatservice.service.impl.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatRoomHandler {

    private final ChatServiceImpl chatService;
    private final Validator validator;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // #3
    public Mono<ServerResponse> insert(ServerRequest request) {

        return request.bodyToMono(ChatRoomNameModel.class)
                .doOnNext(roomModel -> {
                    var errors = new BeanPropertyBindingResult(roomModel, ChatRoomNameModel.class.getName());
                    validator.validate(roomModel, errors);

                    if (errors.hasErrors()) {
                        throw new IllegalArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
                    }
                })
                .flatMap(roomModel -> {
                    return chatService.createRoom(Mono.just(roomModel),  request.queryParam("nickname").orElseThrow())
                            .flatMap(roomId ->
                                    ServerResponse.ok().bodyValue(roomId)
                            );
                })
                .onErrorResume(e -> ServerResponse.ok().bodyValue(false));
    }

    // #7
    public Mono<ServerResponse> findList(ServerRequest request) {
        return Mono.justOrEmpty( request.queryParam("nickname").orElseThrow())
                .flatMap(nickname ->
                        chatService.getChatListByNickname(nickname)
                                .collectList()
                                .flatMap(chatRooms -> {
                                    if (chatRooms.isEmpty()) {
                                        return ServerResponse.ok().bodyValue(false);
                                    } else {
                                        return ServerResponse.ok().bodyValue(chatRooms);
                                    }
                                })
                )
                .switchIfEmpty(ServerResponse.badRequest().bodyValue(false))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(false));
    }


    // # 100
    public Mono<ServerResponse> updateName(ServerRequest request) {
        return request.bodyToMono(JsonNode.class)
                .flatMap(jsonNode -> {
                    var nameModel = objectMapper.convertValue(jsonNode.get(0), ChatRoomNameModel.class);

                    var errors = new BeanPropertyBindingResult(nameModel, ChatRoomNameModel.class.getName());
                    validator.validate(nameModel, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new IllegalArgumentException(errors.getAllErrors().get(0).getDefaultMessage()));
                    }
                    return chatService.updateRoomName(nameModel.getName(), jsonNode.get(1).get("roomId").asText(),  request.queryParam("nickname").orElseThrow())
                            .flatMap(success ->
                                    success
                                            ? ServerResponse.ok().bodyValue(true)
                                            : ServerResponse.ok().bodyValue(false)
                            );
                })
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    // # 101
    public Mono<ServerResponse> updatePassword(ServerRequest request) {
        return request.bodyToMono(JsonNode.class)
                .flatMap(jsonNode -> {
                    var passwordModel = objectMapper.convertValue(jsonNode.get(0), ChatRoomPasswordModel.class);
                    var errors = new BeanPropertyBindingResult(passwordModel, ChatRoomPasswordModel.class.getName());
                    validator.validate(passwordModel, errors);
                    if (errors.hasErrors()) {
                        String errorMessage = errors.getAllErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining(", "));
                        return ServerResponse.badRequest().bodyValue(errorMessage);
                    }
                    return chatService.updateRoomPassword(passwordModel.getPassword(), jsonNode.get(1).get("roomId").asText(),  request.queryParam("nickname").orElseThrow())
                            .flatMap(success ->
                                    success
                                            ? ServerResponse.ok().bodyValue(true)
                                            : ServerResponse.ok().bodyValue(false)
                            );
                })
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    // # 99
    public Mono<ServerResponse> delete(ServerRequest request) {
        return chatService.deleteChatRoom(request.pathVariable("roomId"))
                .flatMap(success ->
                        success
                                ? ServerResponse.ok().bodyValue(true)
                                : ServerResponse.ok().bodyValue(false)
                );
    }

    // # 109
    public Mono<ServerResponse> insertLastReadMessageTime(ServerRequest request) {
        return chatService.insertReadLastTime(request.pathVariable("roomId"), request.queryParam("nickname").orElseThrow())
                .flatMap(success ->
                        success
                                ? ServerResponse.ok().bodyValue(true)
                                : ServerResponse.ok().bodyValue(false)
                );
    }
}
