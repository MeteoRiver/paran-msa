package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
}
