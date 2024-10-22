package com.paranmanzang.groupservice.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<?> getBookList(Pageable pageable);
}