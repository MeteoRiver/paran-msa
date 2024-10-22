package com.paranmanzang.groupservice.model.repository;

import com.paranmanzang.groupservice.model.domain.GroupResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface GroupRepositoryCustom {
    Page<GroupResponseModel> findGroup(Pageable pageable);

    List<GroupResponseModel> findByNickname(String nickname);

    Page<GroupResponseModel> findByEnable(Pageable pageable);


}
