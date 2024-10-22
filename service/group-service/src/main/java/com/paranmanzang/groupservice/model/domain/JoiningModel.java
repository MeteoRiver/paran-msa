package com.paranmanzang.groupservice.model.domain;

import com.paranmanzang.groupservice.model.entity.Joining;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoiningModel {
    @NotBlank(message = "참여할 사용자 닉네임은 필수값입니다.")
    private String nickname;
    @NotNull(message = "참여할 그룹 Id는 필수값입니다.")
    private Long groupId;

    private LocalDate requestAt;
    private LocalDate responseAt;
    private boolean enabled;


    public static JoiningModel fromEntity(Joining joining) {
        return JoiningModel.builder()
                .nickname(joining.getNickname())
                .groupId(joining.getGroup().getId())
                .requestAt(joining.getRequestAt())
                .responseAt(joining.getResponseAt())
                .enabled(joining.isEnabled())
                .build();
    }
}