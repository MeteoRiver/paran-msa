package com.paranmanzang.chatservice.model.domain;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUnReadUserModel {
    private String messageId;
    private String nickname;
    private String roomId;
}
