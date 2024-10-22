package com.paranmanzang.chatservice.config;

import com.paranmanzang.chatservice.controller.ChatUserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ChatUserRoute {
    @Bean
    public RouterFunction<ServerResponse> userRoute(ChatUserHandler chatUserHandler) {
        return // # 4
                route(POST("/api/chats/users").and(accept(MediaType.APPLICATION_JSON)), chatUserHandler::insert)
                        // # 8
                        .andRoute(GET("/api/chats/users/list/{roomId}").and(accept(MediaType.APPLICATION_JSON)), chatUserHandler::findList)
                        // # 9
                        .andRoute(DELETE("/api/chats/users/{roomId}").and(accept(MediaType.APPLICATION_JSON)), chatUserHandler::delete);
    }
}
