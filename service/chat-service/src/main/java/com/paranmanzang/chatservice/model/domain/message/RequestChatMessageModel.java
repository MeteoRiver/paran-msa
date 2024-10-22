package com.paranmanzang.chatservice.model.domain.message;

import com.paranmanzang.chatservice.model.eums.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestChatMessageModel {
    private MessageType type = MessageType.TALK;
    private String roomId;
    private String message;
}
