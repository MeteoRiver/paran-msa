package com.paranmanzang.groupservice.model.repository;

import com.paranmanzang.groupservice.model.entity.LikePosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikePostRepository extends JpaRepository<LikePosts, Long>, LikePostRepositoryCustom {

    LikePosts findByNicknameAndPostId(String nickname, Long postId);

    int deleteByNicknameAndPostId(String nickname, Long postId);

    Boolean existsByNicknameAndPostId(String nickname, Long postId);



}
