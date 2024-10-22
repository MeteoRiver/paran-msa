package com.paranmanzang.userservice.model.domain;

import com.paranmanzang.userservice.model.entity.AdminPosts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(title="관리자 게시판")
@NoArgsConstructor
@AllArgsConstructor
public class AdminPostModel {
    @Schema(title="게시판 id")
    private Long id;

    @Schema(title="게시글 제목")
    private String title;

    @Schema(title="게시글 내용")
    private String content;

    @Schema(title="게시글 종류")
    private String category;

    @Schema(title="글쓴사람 id")
    private String nickname;

    @Schema(title="생성 날짜")
    private LocalDateTime createdDate;

    @Schema(title="조회수")
    private Long viewCount;

    @Schema(title="마지막 수정 날짜")
    private LocalDateTime lastModifiedDate;

    public static AdminPostModel fromEntity(AdminPosts adminPosts) {
        return AdminPostModel.builder()
                .id(adminPosts.getId())
                .title(adminPosts.getTitle())
                .content(adminPosts.getContent())
                .category(adminPosts.getCategory())
                .createdDate(adminPosts.getCreateAt())
                .viewCount(adminPosts.getViewCount())
                .lastModifiedDate(adminPosts.getModifyAt())
                .build();
    }
}