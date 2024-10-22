package com.paranmanzang.groupservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupService {
    Page<?> groupList(Pageable pageable);

    List<?> groupsByUserNickname(String userNickname);

    Object enableGroup(Long groupId);

    Object enableCancelGroup(Long groupId);

    Boolean duplicatename(String groupname);

    Object deleteGroup(Long groupId);

    Object updateChatRoomId(String roomId, Long groupId);

    Page<?> enableGroupList(Pageable pageable);
}
