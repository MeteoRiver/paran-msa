package com.paranmanzang.groupservice.model.repository;

import com.paranmanzang.groupservice.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> , BookRepositoryCustom {
}
