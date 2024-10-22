package com.paranmanzang.roomservice.model.repository.impl;

import com.paranmanzang.roomservice.model.entity.Address;
import com.paranmanzang.roomservice.model.entity.QAddress;
import com.paranmanzang.roomservice.model.repository.AddressCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QAddress address= QAddress.address1;

    @Override
    public List<Address> findQuery(String query, List<Long> roomId) {
        return jpaQueryFactory.selectFrom(address)
                .where(address.address.contains(query).and(address.roomId.in(roomId))).fetch();
    }

    @Override
    public List<Address> findEnabledRoom(List<Long> roomIdLst) {
        return jpaQueryFactory.selectFrom(address).where(address.roomId.in(roomIdLst)).fetch();
    }
}
