package com.paranmanzang.userservice.model.repository;

import com.paranmanzang.userservice.model.entity.AdminPosts;
import com.paranmanzang.userservice.model.repository.custom.AdminPostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminPostRepository extends JpaRepository<AdminPosts, Long>, AdminPostRepositoryCustom {
    AdminPosts getAdminPostsById(Long aboardId); //한 게시글
}
