package com.paranmanzang.groupservice.model.repository;

import com.paranmanzang.groupservice.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, GroupRepositoryCustom {
    boolean existsByName(String name);//적절할까?

    void deleteById(Long groupId);
}
