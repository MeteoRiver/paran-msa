package com.paranmanzang.userservice.model.repository.custom;

import com.paranmanzang.userservice.model.domain.DeclarationPostModel;
import com.paranmanzang.userservice.model.entity.DeclarationPosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeclarationPostRepositoryCustom {
    Page<DeclarationPostModel> findByNickname(String nickname, Pageable pageable);

    Page<DeclarationPostModel> findAllPost(Pageable pageable);
 }
