package com.paranmanzang.groupservice.model.repository.impl;

import com.paranmanzang.groupservice.model.domain.BookResponseModel;
import com.paranmanzang.groupservice.model.repository.BookRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.paranmanzang.groupservice.model.entity.QBook.book;
import static com.paranmanzang.groupservice.model.entity.QLikeBooks.likeBooks;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BookResponseModel> findAllBooks(Pageable pageable) {
        // Step 1: ID만 조회
        var ids = queryFactory
                .select(book.id)
                .from(book)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Step 2: 필요한 필드 조회 및 BookResponseModel 변환
        List<BookResponseModel> books = ids.isEmpty() ? List.of() :
                queryFactory
                        .selectDistinct(Projections.constructor(
                                BookResponseModel.class,
                                book.id.as("id"),
                                book.title.as("title"),
                                book.author.as("author"),
                                book.categoryName.as("categoryName"),
                                book.like_books.size().as("likeBookCount")
                        ))
                        .from(book)
                        .leftJoin(book.like_books, likeBooks)
                        .where(book.id.in(ids))
                        .fetch();


        return new PageImpl<>(books, pageable, ids.size());
    }

}