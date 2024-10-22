package com.paranmanzang.chatservice.model.domain.room;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChatRoomNameModel {
    @NotBlank(message = "채팅방 이름은 필수값입니다.")
    private String name;
}
