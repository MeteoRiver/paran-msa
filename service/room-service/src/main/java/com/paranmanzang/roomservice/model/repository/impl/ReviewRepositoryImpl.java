package com.paranmanzang.roomservice.model.repository.impl;

import com.paranmanzang.roomservice.model.domain.ReviewModel;
import com.paranmanzang.roomservice.model.entity.QReview;
import com.paranmanzang.roomservice.model.repository.ReviewCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QReview review = QReview.review;


    @Override
    public Page<?> findByRoom(Long roomId, Pageable pageable) {
        var result=  jpaQueryFactory.select(
                        Projections.constructor(
                                ReviewModel.class,
                                review.id.as("id"),
                                review.rating.as("rating"),
                                review.content.as("content"),
                                review.nickname.as("nickname"),
                                review.createAt.as("createAt"),
                                review.room.id.as("roomId"),
                                review.booking.id.as("bookingId")
                        )
                )
                .from(review)
                .where(review.id.in(
                        jpaQueryFactory.select(review.id)
                                .from(review)
                                .where(review.room.id.eq(roomId))
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }

    @Override
    public Page<?> findByUser(String nickname, Pageable pageable) {
        var result=  jpaQueryFactory.select(
                        Projections.constructor(
                                ReviewModel.class,
                                review.id.as("id"),
                                review.rating.as("rating"),
                                review.content.as("content"),
                                review.nickname.as("nickname"),
                                review.createAt.as("createAt"),
                                review.room.id.as("roomId"),
                                review.booking.id.as("bookingId")
                        )
                )
                .from(review)
                .where(review.id.in(
                        jpaQueryFactory.select(review.id)
                                .from(review)
                                .where(review.room.nickname.eq(nickname))
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }

    @Override
    public Page<?> findAll(Pageable pageable) {
        var result=  jpaQueryFactory.select(
                        Projections.constructor(
                                ReviewModel.class,
                                review.id.as("id"),
                                review.rating.as("rating"),
                                review.content.as("content"),
                                review.nickname.as("nickname"),
                                review.createAt.as("createAt"),
                                review.room.id.as("roomId"),
                                review.booking.id.as("bookingId")
                        )
                )
                .from(review)
                .where(review.id.in(
                        jpaQueryFactory.select(review.id)
                                .from(review)
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }
}
