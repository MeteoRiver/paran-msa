package com.paranmanzang.roomservice.service;

import com.paranmanzang.roomservice.model.domain.BookingModel;
import com.paranmanzang.roomservice.model.domain.TimeSaveModel;

import java.util.List;

public interface TImeService {
    Boolean saveList(TimeSaveModel model);
    void saveListScheduled();

    Boolean saveBooking(BookingModel model);
    void deleteByRoom(Long roomId);

    List<?> findByRoom(long roomId);
    List<?> findByBooking(long bookingId);
}
