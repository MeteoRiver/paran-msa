package com.paranmanzang.chatservice.config;

import com.paranmanzang.chatservice.controller.ChatRoomHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ChatRoomRoute {

    @Bean
    public RouterFunction<ServerResponse> roomRoute(ChatRoomHandler chatRoomHandler) {
        return // # 3
                route(POST("/api/chats/rooms").and(accept(MediaType.APPLICATION_JSON)), chatRoomHandler::insert)
                        // # 7
                        .andRoute(GET("/api/chats/rooms/list").and(accept(MediaType.APPLICATION_JSON)), chatRoomHandler::findList)
                        // # 100
                        .andRoute(PUT("/api/chats/rooms/name").and(accept(MediaType.APPLICATION_JSON)), chatRoomHandler::updateName)
                        // # 101
                        .andRoute(PUT("/api/chats/rooms/password").and(accept(MediaType.APPLICATION_JSON)), chatRoomHandler::updatePassword)
                        // # 99
                        .andRoute(DELETE("/api/chats/rooms/{roomId}").and(accept(MediaType.APPLICATION_JSON)), chatRoomHandler::delete)
                        // # 109
                        .andRoute(POST("/api/chats/rooms/last-read-time/{roomId}").and(accept(MediaType.APPLICATION_JSON)), chatRoomHandler::insertLastReadMessageTime);
    }
}
