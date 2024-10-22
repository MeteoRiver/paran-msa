package com.paranmanzang.roomservice.service;


import com.paranmanzang.roomservice.model.domain.LikeRoomModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeRoomService {
    //좋아요
    Object insert(LikeRoomModel likeRoomModel);

    //좋아요 취소
    boolean remove(LikeRoomModel likeRoomModel);

    //마이페이지 공간 찜 조회
    List<?> findAllByUserNickname(String nickname);

}
