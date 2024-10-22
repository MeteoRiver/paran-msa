package com.paranmanzang.commentservice.service;


import com.paranmanzang.commentservice.model.domain.CommentRequestModel;
import com.paranmanzang.commentservice.model.domain.CommentResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    Boolean insert(CommentRequestModel model, String nickname);

    Boolean delete(Long commentId);

    Boolean update(Long commentId, String content, String nickname);

    Page<?> getCommentListByPostId(Long postId, Pageable pageable);

}
