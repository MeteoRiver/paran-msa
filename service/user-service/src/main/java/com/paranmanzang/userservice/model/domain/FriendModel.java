package com.paranmanzang.userservice.model.domain;

import com.paranmanzang.userservice.model.entity.Friends;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title="친구")
public class FriendModel {
    @Schema(title="친구 테이블 id")
    private Long id;
    @Schema(title="친구 요청 받는 사람")
    private String responseUser;
    @Schema(title="친구 요청 한 사람")
    private String requestUser;
    @Schema(title="친구 요청 된 시간")
    private LocalDateTime requestAt;
    @Schema(title="상태 변경시 시간")
    private LocalDateTime responseAt;

    public static FriendModel fromEntity(Friends friends) {
        return FriendModel.builder()
                .id(friends.getId())
                .responseUser(friends.getResponseUser())
                .requestUser(friends.getRequestUser())
                .requestAt(friends.getRequest_at())
                .responseAt(friends.getResponse_at())
                .build();
    }
}
