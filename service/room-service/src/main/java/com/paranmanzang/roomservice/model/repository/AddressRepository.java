package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, AddressCustomRepository {
}
