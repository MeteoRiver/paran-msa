package com.paranmanzang.userservice.model.repository.custom;

import com.paranmanzang.userservice.model.domain.FriendModel;
import com.paranmanzang.userservice.model.entity.Friends;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepositoryCustom {
    List<FriendModel> findFriendByRequestUser(String requestUser);

    Boolean existsByRequestUserAndResponseUser(String requestUser, String responseUser);

    Boolean existsRequestByRequestUserAndResponseUser(String requestUser, String responseUser);

    List<FriendModel> findFriendRequestByRequestUser(String nickname);

    Friends findFriendRequestByRequestUserAndResponseUser(String nickname1, String nickname2);

}
