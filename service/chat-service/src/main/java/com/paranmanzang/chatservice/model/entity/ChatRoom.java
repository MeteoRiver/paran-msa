package com.paranmanzang.chatservice.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "chat_room")
public class ChatRoom {

    @Id
    private String id;

    private String name;

    @CreatedDate
    private LocalDateTime createAt;

    private String password; // 4~10 All 숫자

}

