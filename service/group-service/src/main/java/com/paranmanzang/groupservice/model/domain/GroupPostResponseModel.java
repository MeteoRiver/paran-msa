package com.paranmanzang.groupservice.model.domain;

import com.paranmanzang.groupservice.model.entity.GroupPost;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupPostResponseModel {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDate modifyAt;
    private String postCategory;
    private int viewCount;
    private String nickname;
    private Long groupId;      // 그룹 ID
    private String groupName;  // 그룹 이름 (필요시)
    private Long bookId;       // 책 ID
    private String bookTitle;  // 책 제목 (필요시)

    public static GroupPostResponseModel fromEntity(GroupPost groupPost) {
        return GroupPostResponseModel.builder()
                .id(groupPost.getId())
                .title(groupPost.getTitle())
                .content(groupPost.getContent())
                .createAt(groupPost.getCreateAt())
                .modifyAt(groupPost.getModifyAt())
                .postCategory(groupPost.getPostCategory())
                .viewCount(groupPost.getViewCount())
                .nickname(groupPost.getNickname())
                .groupId(groupPost.getGroup() != null ? groupPost.getGroup().getId() : null)
                .groupName(groupPost.getGroup() != null ? groupPost.getGroup().getName() : null)
                .bookId(groupPost.getBook() != null ? groupPost.getBook().getId() : null)
                .bookTitle(groupPost.getBook() != null ? groupPost.getBook().getTitle() : null)  // 필요시 책 제목 포함
                .build();
    }
}