package com.paranmanzang.roomservice.model.repository;


import com.paranmanzang.roomservice.model.domain.LikeRoomModel;

import java.util.List;


public interface LikeRoomRepositoryCustom {
    List<LikeRoomModel> findLikeIdByNickname(String nickname);
}
