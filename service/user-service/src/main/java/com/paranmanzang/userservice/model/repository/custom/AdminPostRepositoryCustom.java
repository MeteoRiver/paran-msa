package com.paranmanzang.userservice.model.repository.custom;

import com.paranmanzang.userservice.model.domain.AdminPostModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdminPostRepositoryCustom {
    Page<AdminPostModel> findAdminPostByNickname(String nickname, Pageable pageable);

    Page<AdminPostModel> findAllPost(Pageable pageable);
}
