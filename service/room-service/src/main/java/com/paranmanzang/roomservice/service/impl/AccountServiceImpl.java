package com.paranmanzang.roomservice.service.impl;

import com.paranmanzang.roomservice.model.domain.AccountCancelModel;
import com.paranmanzang.roomservice.model.domain.AccountModel;
import com.paranmanzang.roomservice.model.domain.AccountResultModel;
import com.paranmanzang.roomservice.model.entity.Account;
import com.paranmanzang.roomservice.model.repository.AccountRepository;
import com.paranmanzang.roomservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;


    @Override
    public Boolean insert(AccountResultModel model) {

        return accountRepository.save(Account.builder()
                .orderId(model.getOrderId())
                .detail(model.getOrderName())
                .payToken(model.getPaymentKey())
                .usePoint(model.getUsePoint())
                .amount(model.getAmount())
                .roomId(model.getRoomId())
                .groupId(model.getGroupId())
                .bookingId(model.getBookingId())
                .createAt(LocalDateTime.now())
                .build()) == null ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public String findByOrderId(String orderId) {
        return accountRepository.findPaymentKeyByOrderId(orderId);
    }

    @Override
    public Boolean cancel(AccountCancelModel model) {
        return accountRepository.findAccountByPaymentKey(model.getPaymentKey()).map(account -> {
            account.setCanceled(true);
            account.setReason(model.getCancelReason());
            return accountRepository.save(account);
        }) == null ? Boolean.FALSE : Boolean.TRUE;

    }

    @Override
    public Boolean cancel(Long bookingId) {
        return accountRepository.findAccountByBookingId(bookingId)
                .map(account -> {
                    account.setCanceled(true);
                    account.setReason("예약 취소");
                    return accountRepository.save(account);
                })
                .isPresent();

    }

    @Override
    public AccountModel findByBookingId(Long bookingId) {
        return accountRepository.findAccountByBookingId(bookingId).map(account ->
                        AccountModel.builder()
                                .orderId(account.getOrderId())
                                .orderName(account.getDetail())
                                .amountTaxFree(account.getAmountTaxFree())
                                .reason(account.getReason())
                                .amount(account.getAmount())
                                .bookingId(account.getBookingId())
                                .roomId(account.getRoomId())
                                .groupId(account.getGroupId())
                                .usePoint(account.getUsePoint())
                                .canceled(account.isCanceled())
                                .createAt(account.getCreateAt())
                                .build()
                )
                .orElse(null);
    }

    @Override
    public Page<?> findByRoomId(Long roomId, Pageable pageable) {
        return accountRepository.findAccountByRoomId(roomId, pageable);
    }

    @Override
    public Page<?> findByGroupId(Long groupId, Pageable pageable) {
        return accountRepository.findAccountByGroupId(groupId, pageable);
    }
}
