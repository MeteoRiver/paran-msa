package com.paranmanzang.chatservice.model.domain;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUserModel {
    private String nickname;
    private LocalDateTime enterTime;
}
