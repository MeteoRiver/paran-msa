package com.paranmanzang.userservice.service.impl;

import com.paranmanzang.userservice.model.domain.AdminPostModel;
import com.paranmanzang.userservice.model.entity.AdminPosts;
import com.paranmanzang.userservice.model.repository.AdminPostRepository;
import com.paranmanzang.userservice.service.AdminPostService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.paranmanzang.userservice.model.entity.QAdminPosts.adminPosts;

@Service
@Transactional
public class AdminPostServiceImpl implements AdminPostService {

    private final AdminPostRepository adminPostRepository;

    public AdminPostServiceImpl(AdminPostRepository adminPostRepository) {
        this.adminPostRepository = adminPostRepository;
    }

    // 게시글 작성
    @Override
    public Object insert(AdminPostModel adminPostModel) {

        AdminPosts savedPost = adminPostRepository.save(AdminPosts.builder()
                .title(adminPostModel.getTitle())
                .content(adminPostModel.getContent())
                .category(adminPostModel.getCategory())
                .nickname(adminPostModel.getNickname())
                .build());

        return AdminPostModel.fromEntity(savedPost);
    }

    // 게시글 수정
    @Override
    public Object update(Long id, AdminPostModel adminPostModel) {
        return adminPostRepository.findById(id)
                .map(existingPost -> {
                    existingPost.setTitle(adminPostModel.getTitle());
                    existingPost.setContent(adminPostModel.getContent());
                    existingPost.setCategory(adminPostModel.getCategory());
                    return AdminPostModel.fromEntity(adminPostRepository.save(existingPost));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));
    }

    // 게시글 삭제
    @Override
    public boolean remove(Long id) {
        try {
            if (adminPostRepository.existsById(id)) {
                adminPostRepository.deleteById(id);
                return !adminPostRepository.existsById(id);
            }
            return false;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    // 마이페이지에서 리스트 조회
    @Override
    public Page<AdminPostModel> findAllByNickname(String nickname, Pageable pageable) {
        return adminPostRepository.findAdminPostByNickname(nickname, pageable);
    }


    // 게시글 리스트 조회
    @Override
    public Page<AdminPostModel> findAll(Pageable pageable) {
        return adminPostRepository.findAllPost(pageable);
    }

    // 게시글 상세 조회
    @Override
    public AdminPosts findByAdminPostId(Long id) {
        Optional<AdminPosts> postOptional = adminPostRepository.findById(id); // Optional로 게시물 조회
        if (postOptional.isPresent()){
            AdminPosts post = postOptional.get();
            post.setViewCount(post.getViewCount() + 1); // 조회수 증가
            adminPostRepository.save(post); // 변경 사항 저장
            return post;
        }
        else{
            return null;
        }
    }

    // 조회수 확인
    @Override
    public Long findViewCountById(Long id) {
        return adminPostRepository.findById(id)
                .map(AdminPosts::getViewCount)
                .orElse(null);
    }
}
