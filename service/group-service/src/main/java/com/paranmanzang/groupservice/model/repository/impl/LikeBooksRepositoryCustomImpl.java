package com.paranmanzang.groupservice.model.repository.impl;

import com.paranmanzang.groupservice.model.domain.BookResponseModel;
import com.paranmanzang.groupservice.model.domain.LikeBookModel;
import com.paranmanzang.groupservice.model.repository.LikeBooksRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.paranmanzang.groupservice.model.entity.QBook.book;
import static com.paranmanzang.groupservice.model.entity.QLikeBooks.likeBooks;

@RequiredArgsConstructor
public class LikeBooksRepositoryCustomImpl implements LikeBooksRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookResponseModel> findLikeBooksByNickname(String nickname) {
        var ids = queryFactory
                .select(likeBooks.book.id)
                .from(likeBooks)
                .where(likeBooks.nickname.eq(nickname))
                .fetch();

        return ids.isEmpty() ? List.of() :
                queryFactory
                        .select(Projections.constructor(
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
    }
}
