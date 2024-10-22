package com.paranmanzang.groupservice.service;

import com.paranmanzang.groupservice.model.domain.GroupPostModel;
import com.paranmanzang.groupservice.model.domain.GroupPostResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupPostService {

    Page<GroupPostResponseModel> findByGroupId(Long groupId, Pageable pageable, String postCategory);

    GroupPostResponseModel savePost(GroupPostModel groupPostModel);

    Object updateViewCount(Long postId);
}
