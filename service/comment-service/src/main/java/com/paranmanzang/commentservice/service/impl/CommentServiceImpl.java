package com.paranmanzang.commentservice.service.impl;

import com.paranmanzang.commentservice.model.domain.CommentRequestModel;
import com.paranmanzang.commentservice.model.domain.CommentResponseModel;
import com.paranmanzang.commentservice.model.entity.Comment;
import com.paranmanzang.commentservice.model.repository.CommentRepository;
import com.paranmanzang.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;


    @Override
    public Boolean insert(CommentRequestModel model, String nickname) {
        if (model.getDepth() == 0 && model.getParentId() == null) {// 새 댓글 작성
            repository.save(Comment.builder()
                    .step(1)
                    .ref(repository.findRefByPostId(model.getPostId()) + 1)
                    .depth(0)
                    .content(model.getContent())
                    .nickname(nickname)
                    .postId(model.getPostId())
                    .build());
        } else {// 대댓글 작성
            repository.findById(model.getParentId()).ifPresent(parentComment -> {
                Comment save = repository.save(Comment.builder()
                        .step(parentComment.getStep() + 1)
                        .ref(parentComment.getRef())
                        .depth(model.getDepth() + 1)
                        .content(model.getContent())
                        .nickname(nickname)
                        .postId(model.getPostId())
                        .build());
                repository.updateIncreaseStep(save.getRef(), save.getStep());
            });
        }
        return true;
    }



    @Override
    public Boolean delete(Long commentId) {
        return repository.updateCommentById(commentId);
    }

    @Override
    public Boolean update(Long commentId, String content, String nickname) {
        return repository.update(commentId, content, nickname);
    }

    @Override
    public Page<CommentResponseModel> getCommentListByPostId(Long postId, Pageable pageable) {
        return repository.findCommentByPostId(postId, pageable);
    }
}
