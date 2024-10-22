package com.paranmanzang.groupservice.model.repository;

import com.paranmanzang.groupservice.model.entity.Joining;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JoiningRepository extends JpaRepository<Joining, Long> {
    Optional<Joining> findJoiningByGroupIdAndNickname(Long groupId, String nickname);

    Optional<Joining> findByGroupIdAndNickname(Long groupId, String nickname);

    List<Joining> findByGroupId(Long groupId);
}
