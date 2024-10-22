package com.paranmanzang.commentservice.model.repository;


import com.paranmanzang.commentservice.model.domain.CommentResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomCommentRepository {

    Page<CommentResponseModel> findCommentByPostId(Long postId, Pageable pageable);

    Boolean updateIncreaseStep(int ref, int step);

    int findRefByPostId(Long postId);

    Boolean updateCommentById(Long commentId);

    Boolean update(Long commentId, String content, String nickname);
}
