package com.paranmanzang.commentservice.model.repository.impl;

import com.paranmanzang.commentservice.model.domain.CommentResponseModel;
import com.paranmanzang.commentservice.model.entity.QComment;
import com.paranmanzang.commentservice.model.repository.CustomCommentRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.paranmanzang.commentservice.model.entity.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommentResponseModel> findCommentByPostId(Long postId, Pageable pageable) {

        var ids = queryFactory
                .select(comment.id)
                .from(comment)
                .where(comment.postId.eq(postId))
                .orderBy(comment.ref.desc(), comment.step.asc(), comment.depth.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        List<CommentResponseModel> comments = ids.isEmpty() ? List.of() :
                queryFactory
                        .select(Projections.constructor(
                                CommentResponseModel.class,
                                comment.id.as("id"),
                                comment.nickname.as("nickname"),
                                comment.content.as("content"),
                                comment.postId.as("postId"),
                                comment.ref.as("ref"),
                                comment.step.as("step"),
                                comment.depth.as("depth"),
                                comment.createdAt.as("createdAt")
                        ))
                        .from(comment)
                        .where(comment.id.in(ids))                   // 조회된 ID 리스트 기반으로 필터링
                        .fetch();

        return new PageImpl<>(comments, pageable, ids.size());
    }

    @Override
    public Boolean updateIncreaseStep(int ref, int step) {
        var comment = QComment.comment;
        long affectedRows = queryFactory
                .update(comment)
                .set(comment.step, comment.step.add(1))
                .where(comment.ref.eq(ref)
                        .and(comment.step.gt(step)))
                .execute();

        return affectedRows > 0;
    }

    @Override
    public int findRefByPostId(Long postId) {
        var comment = QComment.comment;
        Integer maxRef = queryFactory
                .select(comment.ref.max())
                .from(comment)
                .where(comment.postId.eq(postId))
                .fetchOne();

        return maxRef != null ? maxRef : 0;
    }

    @Override
    public Boolean updateCommentById(Long commentId) {
        var comment = QComment.comment;
        long affectedRows = queryFactory
                .update(comment)
                .set(comment.content, "삭제된 댓글입니다.")
                .where(comment.id.eq(commentId))
                .execute();

        return affectedRows > 0;
    }

    @Override
    public Boolean update(Long commentId, String content, String nickname) {
        var comment = QComment.comment;
        long affectedRows = queryFactory
                .update(comment)
                .set(comment.content, content)
                .where(comment.id.eq(commentId)
                        .and(comment.nickname.eq(nickname)))
                .execute();

        return affectedRows > 0;
    }
}
