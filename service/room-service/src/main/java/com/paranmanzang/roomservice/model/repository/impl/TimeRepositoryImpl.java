package com.paranmanzang.roomservice.model.repository.impl;

import com.paranmanzang.roomservice.model.domain.BookingModel;
import com.paranmanzang.roomservice.model.entity.QTime;
import com.paranmanzang.roomservice.model.entity.Time;
import com.paranmanzang.roomservice.model.repository.TimeCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TimeRepositoryImpl implements TimeCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QTime time = QTime.time1;

    @Override
    public List<Time> findByRoomId(Long roomId) {
        return jpaQueryFactory.selectFrom(time)
                .where(time.room.id.eq(roomId))
                .fetch();
    }

    @Override
    public List<Time> findByBookingId(Long bookingId) {
        return jpaQueryFactory.selectFrom(time)
                .where(time.booking.id.eq(bookingId))
                .fetch();
    }

    @Override
    public List<Time> findByBooking(BookingModel model) {
        return jpaQueryFactory.selectFrom(time)
                .where(
                        time.date
                                .eq(model.getDate())
                                .and(time.time.in(model.getUsingTime()))
                                .and(time.room.id.eq(model.getRoomId()))
                )
                .fetch();
    }

    @Override
    public void deleteByRoom(Long id) {
        jpaQueryFactory.delete(time).where(time.room.id.eq(id));
    }
}
