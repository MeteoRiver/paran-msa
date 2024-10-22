package com.paranmanzang.userservice.service;

import com.paranmanzang.userservice.model.domain.FriendModel;
import com.paranmanzang.userservice.model.entity.Friends;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FriendService {

    //친구 요청 전송
    Object insertRequest(FriendModel friend);

    //친구 요청 수락 여부
    boolean state(FriendModel friendmodel, Boolean state );
    //친구 추가
    Object update(FriendModel friendModel);

    //친구 삭제
    boolean remove(Long id);

    //친구 리스트 조회
    List<FriendModel> findAllByNickname(String nickname) ;

    //친구 요청 리스트 조회
    List<FriendModel> findAllRequestByNickname(String nickname);


}
