package com.paranmanzang.roomservice.model.repository.impl;

import com.paranmanzang.roomservice.model.domain.AccountModel;
import com.paranmanzang.roomservice.model.entity.Account;
import com.paranmanzang.roomservice.model.entity.QAccount;
import com.paranmanzang.roomservice.model.repository.AccountCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QAccount account = QAccount.account;

    @Override
    public String findPaymentKeyByOrderId(String orderId) {
        return jpaQueryFactory.select(account.payToken).from(account).where(account.orderId.eq(orderId)).fetchFirst();
    }

    @Override
    public Optional<Account> findAccountByBookingId(Long bookingId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(account)
                .where(account.bookingId.eq(bookingId)).fetchFirst());
    }

    @Override
    public Optional<Account> findAccountByPaymentKey(String payment) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(account).where(account.payToken.eq(payment)).fetchFirst());
    }

    @Override
    public Page<?> findAccountByGroupId(Long groupId, Pageable pageable) {
        var result= jpaQueryFactory.select(
                        Projections.constructor(
                                AccountModel.class,
                                account.orderId.as("orderId"),
                                account.detail.as("orderName"),
                                account.amount.as("amount"),
                                account.amountTaxFree.as("amountTaxFree"),
                                account.usePoint.as("usePoint"),
                                account.canceled.as("canceled"),
                                account.reason.as("reason"),
                                account.createAt.as("createAt"),
                                account.groupId.as("groupId"),
                                account.roomId.as("roomId"),
                                account.bookingId.as("bookingId")

                        )
                )
                .from(account)
                .where(account.orderId.in(
                        jpaQueryFactory.select(account.orderId)
                                .from(account)
                                .where(account.groupId.eq(groupId))
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        log.info("result: "+ result);
        return new PageImpl<>( result, pageable, result.size());

    }

    @Override
    public Page<?> findAccountByRoomId(Long roomId, Pageable pageable) {
        var result= jpaQueryFactory.select(
                        Projections.constructor(
                                AccountModel.class,
                                account.orderId.as("orderId"),
                                account.detail.as("orderName"),
                                account.amount.as("amount"),
                                account.amountTaxFree.as("amountTaxFree"),
                                account.usePoint.as("usePoint"),
                                account.canceled.as("canceled"),
                                account.reason.as("reason"),
                                account.createAt.as("createAt"),
                                account.groupId.as("groupId"),
                                account.roomId.as("roomId"),
                                account.bookingId.as("bookingId")
                        )
                )
                .from(account)
                .where(account.orderId.in(
                        jpaQueryFactory.select(account.orderId)
                                .from(account)
                                .where(account.roomId.eq(roomId))
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                )).fetch();
        return new PageImpl<>( result, pageable, result.size());
    }
}
