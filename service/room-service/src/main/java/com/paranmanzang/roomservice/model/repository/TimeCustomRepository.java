package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.domain.BookingModel;
import com.paranmanzang.roomservice.model.entity.Time;

import java.util.List;

public interface TimeCustomRepository {
    List<Time> findByRoomId( Long roomId);
    List<Time> findByBookingId(Long bookingId);
    List<Time> findByBooking(BookingModel model);
    void deleteByRoom( Long id);
}
