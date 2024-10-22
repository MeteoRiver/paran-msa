package com.paranmanzang.chatservice.config;

import com.paranmanzang.chatservice.controller.ChatMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ChatMessageRoute {
    @Bean
    public RouterFunction<ServerResponse> messageRoute(ChatMessageHandler chatMessageHandler) {
        return route()
                // # 108
                .GET("/api/chats/messages/{roomId}", accept(MediaType.TEXT_EVENT_STREAM), chatMessageHandler::findList)
                // # 6
                .POST("/api/chats/messages", accept(MediaType.APPLICATION_JSON), chatMessageHandler::insert)
                // # 110
                .GET("/api/chats/messages/total-un-read", accept(MediaType.APPLICATION_JSON), chatMessageHandler::findUnReadTotalCount)
                .build();
    }

}
