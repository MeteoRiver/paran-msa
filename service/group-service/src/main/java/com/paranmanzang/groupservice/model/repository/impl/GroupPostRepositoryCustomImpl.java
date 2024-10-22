package com.paranmanzang.groupservice.model.repository.impl;

import com.paranmanzang.groupservice.model.domain.GroupPostResponseModel;
import com.paranmanzang.groupservice.model.repository.GroupPostRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.paranmanzang.groupservice.model.entity.QBook.book;
import static com.paranmanzang.groupservice.model.entity.QGroup.group;
import static com.paranmanzang.groupservice.model.entity.QGroupPost.groupPost;

@RequiredArgsConstructor
public class GroupPostRepositoryCustomImpl implements GroupPostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GroupPostResponseModel> findGroupPostsByGroupId(Long groupId, Pageable pageable, String postCategory) {

        var ids = queryFactory
                .select(groupPost.id)
                .from(groupPost)
                .where(
                        groupPost.group.id.eq(groupId)
                                .and(groupPost.postCategory.eq(postCategory))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Step 2: 필요한 필드 조회 및 GroupPostResponseModel 변환
        List<GroupPostResponseModel> content = ids.isEmpty() ? List.of() :
                queryFactory
                        .selectDistinct(Projections.constructor(
                                GroupPostResponseModel.class,
                                groupPost.id,
                                groupPost.title,
                                groupPost.content,
                                groupPost.createAt,
                                groupPost.modifyAt,
                                groupPost.postCategory,
                                groupPost.viewCount,
                                groupPost.nickname,
                                groupPost.group.id.as("groupId"),
                                groupPost.group.name.as("groupName"),
                                groupPost.book.id.as("bookId"),
                                groupPost.book.title.as("bookTitle")
                        ))
                        .from(groupPost)
                        .leftJoin(groupPost.group, group)  // left join to handle null groups
                        .leftJoin(groupPost.book, book)    // left join to handle null books
                        .where(groupPost.id.in(ids))
                        .fetch();
        System.out.println(content);
        return new PageImpl<>(content, pageable, ids.size());
    }

    @Override
    public GroupPostResponseModel findByPostId(Long postId) {
        return queryFactory
                .selectDistinct(Projections.constructor(
                        GroupPostResponseModel.class,
                        groupPost.id,
                        groupPost.title,
                        groupPost.content,
                        groupPost.createAt,
                        groupPost.modifyAt,
                        groupPost.postCategory,
                        groupPost.viewCount,
                        groupPost.nickname,
                        groupPost.group.id.as("groupId"),
                        groupPost.group.name.as("groupName"),
                        groupPost.book.id.as("bookId"),
                        groupPost.book.title.as("bookTitle")
                ))
                .from(groupPost)
                .leftJoin(groupPost.group, group)
                .leftJoin(groupPost.book, book)
                .where(groupPost.id.eq(postId))
                .fetchOne();
    }
}
