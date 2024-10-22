package com.paranmanzang.userservice.model.repository;

import com.paranmanzang.userservice.model.entity.DeclarationPosts;
import com.paranmanzang.userservice.model.repository.custom.DeclarationPostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationPostRepository extends JpaRepository<DeclarationPosts, Long>, DeclarationPostRepositoryCustom {

}
