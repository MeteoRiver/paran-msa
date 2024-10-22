package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long>, TimeCustomRepository {
}
