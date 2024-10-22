package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, BookingCustomRepository {
}
