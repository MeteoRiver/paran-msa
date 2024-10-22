package com.paranmanzang.roomservice.service;

import com.paranmanzang.roomservice.model.domain.BookingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    BookingModel insert(BookingModel model);
    BookingModel updateState(Long id);
    Boolean delete(Long id);

    Page<?> findByGroup(long groupId, Pageable pageable);

    Page<?> findByRoom(long roomId, Pageable pageable);

    BookingModel findOne(long id);

    Page<?> findByGroups(List<Long> groupIds, Pageable pageable);

    Page<?> findByRooms(String nickname, Pageable pageable);
}
