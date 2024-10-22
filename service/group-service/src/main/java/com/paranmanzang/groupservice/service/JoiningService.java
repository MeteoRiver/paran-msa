package com.paranmanzang.groupservice.service;

import com.paranmanzang.groupservice.model.domain.JoiningModel;

public interface JoiningService {
    Object addMember(JoiningModel joiningModel);

    Object enableMember(Long groupId, String userNickname);

    Object getUserListById(Long groupId);

    Object deleteUser(String nickname, Long groupId);

}
