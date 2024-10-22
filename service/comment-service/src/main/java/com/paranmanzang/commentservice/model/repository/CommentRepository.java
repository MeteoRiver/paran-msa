package com.paranmanzang.commentservice.model.repository;

import com.paranmanzang.commentservice.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {
}
