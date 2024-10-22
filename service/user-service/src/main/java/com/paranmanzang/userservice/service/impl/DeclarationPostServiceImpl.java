package com.paranmanzang.userservice.service.impl;

import com.paranmanzang.userservice.model.domain.DeclarationPostModel;
import com.paranmanzang.userservice.model.entity.DeclarationPosts;
import com.paranmanzang.userservice.model.repository.DeclarationPostRepository;
import com.paranmanzang.userservice.service.DeclarationPostService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeclarationPostServiceImpl implements DeclarationPostService {

    private final DeclarationPostRepository declarationPostRepository;

    // 신고 게시글 작성
    @Override
    public Object insert(DeclarationPostModel declarationPostModel) {
        try {
            if(declarationPostModel.getDeclarer().equals(declarationPostModel.getTarget())){
                return false;
            }
            DeclarationPosts createPost = declarationPostRepository.save(DeclarationPosts.builder()
                    .title(declarationPostModel.getTitle())
                    .content(declarationPostModel.getContent())
                    .target(declarationPostModel.getTarget())
                    .declarer(declarationPostModel.getDeclarer())
                    .build());

            return DeclarationPostModel.fromEntity(createPost);
        } catch (DataAccessException e) {
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
    // 신고 게시글 삭제(수락 누르면 프론트에서 신고횟수 추가 메서드 호출 후 삭제, 거절 누르면 그냥 삭제)
    public boolean remove(Long id) {
        try{
            if (declarationPostRepository.existsById(id)) {
                declarationPostRepository.deleteById(id);
                return !declarationPostRepository.existsById(id);
            }
            return false;
        }catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    // 신고 게시글 전체 조회 (관리자만)
    @Override
    public Page<DeclarationPostModel> findAll(Pageable pageable) {

            /*if (user.getRole().equals("ROLE_ADMIN")) {
                return declarationPostRepository.findAllPost(pageable);
            }*/
        return declarationPostRepository.findAllPost(pageable);

    }
    //신고자 본인 신고내역 조회
    @Override
    public Page<DeclarationPostModel> findAllByNickname(String nickname, Pageable pageable){
        return declarationPostRepository.findByNickname(nickname, pageable);
    }

    /*@Override
    public Page<AdminPostModel> findAll(Pageable pageable) {
        return adminPostRepository.findAllPost(pageable);
    }*/
    // 신고 게시글 상세 조회
    @Override
    public Object findByPostId(Long postId) {
        try {
            DeclarationPosts post = declarationPostRepository.findById(postId).orElse(null);
            if (post == null) {
                return false;
            }
            return post;
        } catch (DataAccessException e) {
            // 데이터 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;
        }
    }


}
