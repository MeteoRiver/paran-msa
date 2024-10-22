package com.paranmanzang.roomservice.model.repository.impl;

import com.paranmanzang.roomservice.model.entity.Booking;
import com.paranmanzang.roomservice.model.entity.QBooking;
import com.paranmanzang.roomservice.model.repository.BookingCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QBooking booking= QBooking.booking;


    @Override
    public Page<Booking> findByGroupId(Long id, Pageable pageable) {
        var result= jpaQueryFactory.selectFrom(booking)
                .where(booking.id.in(
                        jpaQueryFactory.select(booking.id)
                                .from(booking)
                                .where(booking.groupId.eq(id))
                                .limit( pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }

    @Override
    public Page<Booking> findByRoomId(Long id, Pageable pageable) {
        var result= jpaQueryFactory.selectFrom(booking)
                .where(booking.id.in(
                        jpaQueryFactory.select(booking.id)
                                .from(booking)
                                .where(booking.room.id.eq(id))
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }

    @Override
    public Page<Booking> findByGroupIds(List<Long> groupIds, Pageable pageable) {
        var result= jpaQueryFactory.selectFrom(booking)
                .where(booking.id.in(
                        jpaQueryFactory.select(booking.id)
                                .from(booking)
                                .where(booking.groupId.in(groupIds))
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }

    @Override
    public Page<Booking> findByRoomIds(List<Long> roomIds, Pageable pageable) {
        var result= jpaQueryFactory.selectFrom(booking)
                .where(booking.id.in(
                        jpaQueryFactory.select(booking.id)
                                .from(booking)
                                .where(booking.room.id.in(roomIds))
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }
}
