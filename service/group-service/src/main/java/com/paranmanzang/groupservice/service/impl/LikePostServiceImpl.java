package com.paranmanzang.groupservice.service.impl;


import com.paranmanzang.groupservice.model.domain.GroupPostResponseModel;
import com.paranmanzang.groupservice.model.domain.LikePostModel;
import com.paranmanzang.groupservice.model.entity.LikePosts;
import com.paranmanzang.groupservice.model.repository.GroupPostRepository;
import com.paranmanzang.groupservice.model.repository.LikePostRepository;
import com.paranmanzang.groupservice.service.LikePostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikePostServiceImpl implements LikePostService {
    private final LikePostRepository likePostRepository;
    private final GroupPostRepository groupPostRepository;


    @Override
    public List<GroupPostResponseModel> findAllByNickname(String userNickname) {
        return likePostRepository.findLikePostByNickname(userNickname);
    }
    @Override
    public Object insert(LikePostModel likePostModel) {
        String nickname = likePostModel.getNickname();
        Long postId = likePostModel.getPostId();

        try {
            if (likePostRepository.existsByNicknameAndPostId(nickname, postId)) {
                return false; // 이미 좋아요를 눌렀으면 false 반환
            }

            LikePosts likePosts = new LikePosts();
            likePosts.setPostId(postId);
            likePosts.setNickname(nickname);
            likePostRepository.save(likePosts);
            return groupPostRepository.findByPostId(postId);
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            // 비즈니스 로직 예외 처리
            System.err.println("비즈니스 로직 오류 발생: " + e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean remove(LikePostModel likePostModel) {
        try {
            LikePosts likePosts = likePostRepository.findByNicknameAndPostId(likePostModel.getNickname(), likePostModel.getPostId());
            if (likePosts != null) {
                likePostRepository.deleteByNicknameAndPostId(likePostModel.getNickname(), likePostModel.getPostId());
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
}
