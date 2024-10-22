package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountCustomRepository {
    String findPaymentKeyByOrderId( String orderId);
    Optional<Account> findAccountByBookingId(Long bookingId);
    Optional<Account> findAccountByPaymentKey(String payment);
    Page<?> findAccountByGroupId(Long groupId, Pageable pageable);
    Page<?> findAccountByRoomId(Long roomId, Pageable pageable);
}
