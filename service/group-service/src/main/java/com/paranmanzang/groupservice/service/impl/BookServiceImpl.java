package com.paranmanzang.groupservice.service.impl;

import com.paranmanzang.groupservice.model.domain.BookResponseModel;
import com.paranmanzang.groupservice.model.repository.BookRepository;
import com.paranmanzang.groupservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;


    @Override
    public Page<BookResponseModel> getBookList(Pageable pageable) {
        return bookRepository.findAllBooks(pageable);
    }
}
