package com.paranmanzang.groupservice.service.impl;


import com.paranmanzang.groupservice.model.domain.GroupPostModel;
import com.paranmanzang.groupservice.model.domain.GroupPostResponseModel;
import com.paranmanzang.groupservice.model.repository.GroupPostRepository;
import com.paranmanzang.groupservice.service.GroupPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupPostServiceImpl implements GroupPostService {
    private final GroupPostRepository groupPostRepository;

    public Page<GroupPostResponseModel> findByGroupId(Long groupId, Pageable pageable, String postCategory) {
        return groupPostRepository.findGroupPostsByGroupId(groupId, pageable, postCategory);
    }

    public GroupPostResponseModel savePost(GroupPostModel groupPostModel) {
        return GroupPostResponseModel.fromEntity(groupPostRepository.save(groupPostModel.toEntity()));
    }

    @Transactional
    public Object updatePost(GroupPostModel groupPostModel) {
        return groupPostRepository.findById(groupPostModel.getBoardId())
                .map(boardtoModify -> {
                    boardtoModify.setTitle(groupPostModel.getTitle());
                    boardtoModify.setContent(groupPostModel.getContent());
                    return GroupPostResponseModel.fromEntity(groupPostRepository.save(boardtoModify));
                });
    }


    public Object deletePost(Long boardId) {
        return groupPostRepository.findById(boardId)
                .map(board -> {
                    groupPostRepository.deleteById(boardId);
                    return Boolean.TRUE;
                })
                .orElse(Boolean.FALSE);
    }

    @Override
    public Object updateViewCount(Long postId) {
        return groupPostRepository.findById(postId)
                .map(post -> {
                    post.setViewCount(post.getViewCount() + 1);
                    return GroupPostResponseModel.fromEntity(groupPostRepository.save(post));
                });
    }

}
