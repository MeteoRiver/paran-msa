package com.paranmanzang.groupservice.model.domain;

import com.paranmanzang.groupservice.model.entity.LikeBooks;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeBookModel {
    private long id;
    @NotBlank(message = "닉네임은 필수 값입니다..")
    private String nickname;
    @NotBlank(message = "도서 아이디는 필수값입니다.")
    private long bookId;


    public static LikeBookModel fromEntity(LikeBooks entity) {
        return LikeBookModel.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .bookId(entity.getBook() != null ? entity.getBook().getId() : 0L)
                .build();
    }
}
