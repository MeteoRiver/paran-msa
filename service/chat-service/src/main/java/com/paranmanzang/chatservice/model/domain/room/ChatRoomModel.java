package com.paranmanzang.chatservice.model.domain.room;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomModel {
    private String roomId;
    private String name;
    private String password;
    private int userCount;
    private int unReadMessageCount;
}
