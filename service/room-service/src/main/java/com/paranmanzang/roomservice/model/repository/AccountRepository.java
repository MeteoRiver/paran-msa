package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>, AccountCustomRepository {
}
