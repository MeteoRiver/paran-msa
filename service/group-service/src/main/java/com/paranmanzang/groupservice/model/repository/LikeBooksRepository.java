package com.paranmanzang.groupservice.model.repository;

import com.paranmanzang.groupservice.model.entity.Book;
import com.paranmanzang.groupservice.model.entity.LikeBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeBooksRepository extends JpaRepository<LikeBooks, Long>, LikeBooksRepositoryCustom {
    boolean existsByNicknameAndBook(String nickname, Book book);
}
