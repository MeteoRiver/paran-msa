package com.paranmanzang.roomservice.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewCustomRepository {
    Page<?> findByRoom(Long roomId, Pageable pageable);
    Page<?> findByUser(String nickname, Pageable pageable);
    Page<?> findAll( Pageable pageable);

}
