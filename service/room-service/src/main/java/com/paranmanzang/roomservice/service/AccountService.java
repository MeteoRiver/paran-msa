package com.paranmanzang.roomservice.service;

import com.paranmanzang.roomservice.model.domain.AccountCancelModel;
import com.paranmanzang.roomservice.model.domain.AccountModel;
import com.paranmanzang.roomservice.model.domain.AccountResultModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Boolean insert(AccountResultModel model);

    String findByOrderId(String orderId);

    Boolean cancel(AccountCancelModel model);

    Boolean cancel(Long bookingId);

    AccountModel findByBookingId(Long bookingId);

    Page<?> findByRoomId(Long roomId, Pageable pageable);

    Page<?> findByGroupId(Long groupId, Pageable pageable);
}
