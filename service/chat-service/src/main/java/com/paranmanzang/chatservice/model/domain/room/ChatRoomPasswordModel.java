package com.paranmanzang.chatservice.model.domain.room;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ChatRoomPasswordModel {
    @Size(min = 4, max = 10, message = "비밀번호는 길이가 4~10인 숫자여야 합니다. ")
    @Pattern(regexp = "^[0-9]*$", message = "숫자만 가능합니다.")
    private String password;
}
