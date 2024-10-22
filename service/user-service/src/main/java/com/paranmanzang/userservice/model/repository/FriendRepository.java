package com.paranmanzang.userservice.model.repository;

import com.paranmanzang.userservice.model.entity.Friends;
import com.paranmanzang.userservice.model.repository.custom.FriendRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long>, FriendRepositoryCustom {

}
