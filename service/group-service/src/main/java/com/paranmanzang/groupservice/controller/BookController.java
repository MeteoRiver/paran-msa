package com.paranmanzang.groupservice.controller;


import com.paranmanzang.groupservice.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups/books")
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;

    @GetMapping
    public ResponseEntity<?> findList(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(bookService.getBookList(PageRequest.of(page, size)));
    }
}
