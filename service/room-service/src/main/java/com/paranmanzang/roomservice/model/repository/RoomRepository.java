package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, RoomCustomRepository {
}