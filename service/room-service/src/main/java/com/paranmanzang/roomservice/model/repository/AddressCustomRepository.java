package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.entity.Address;

import java.util.List;

public interface AddressCustomRepository {
    List<Address> findQuery(String query, List<Long> roomIdList);
    List<Address> findEnabledRoom(List<Long> roomIdLst);
}
