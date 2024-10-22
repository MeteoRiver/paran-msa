package com.paranmanzang.chatservice.model.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "chat_user_time_stamp")
public class ChatUserTimeStamp {

    private LocalDateTime lastReadMessageTime;

    private String nickname;

    private String roomId;

}
