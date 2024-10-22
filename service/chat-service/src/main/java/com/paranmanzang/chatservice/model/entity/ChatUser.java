package com.paranmanzang.chatservice.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "chat_user")
public class ChatUser {

    @Id
    private String id;

    private String nickname;

    private String roomId;

    private LocalDateTime enterTime;
}
