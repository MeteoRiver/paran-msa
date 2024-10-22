package com.paranmanzang.roomservice.model.domain;

import com.paranmanzang.roomservice.model.entity.LikeRooms;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LikeRoomModel {
    private Long id;
    private Long roomId;
    private String nickname;

    public static LikeRoomModel fromEntity(LikeRooms likeRooms) {
        return LikeRoomModel.builder()
                .id(likeRooms.getId())
                .roomId(likeRooms.getRoomId())
                .nickname(likeRooms.getNickname())
                .build();
    }
}
