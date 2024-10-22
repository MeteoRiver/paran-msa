package com.paranmanzang.commentservice.model.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestModel {


    @NotEmpty(message = "댓글 내용이 있어야 합니다.")
    private String content;

    @NotNull(message = "게시판 아이디는 필수 값입니다.")
    private Long postId;

    private int step;

    private int depth;

    private Long parentId;
}
