package com.paranmanzang.userservice.service;

import com.paranmanzang.userservice.model.domain.AdminPostModel;
import com.paranmanzang.userservice.model.entity.AdminPosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdminPostService {
    //게시글 작성
    Object insert(AdminPostModel adminPostModel);
    //게시글 업데이트
    Object update(Long id, AdminPostModel adminPostModel);
    //게시글 삭제
    boolean remove(Long id);
    //내가 쓴 게시글 확인
    Page<AdminPostModel> findAllByNickname(String nickname, Pageable pageable);
    //게시판 들어가면 뜨는거
    Page<AdminPostModel> findAll(Pageable pageable);
    //게시글 상세조회
    AdminPosts findByAdminPostId(Long id);
    //조회수 확인
    Long findViewCountById(Long id);
}


