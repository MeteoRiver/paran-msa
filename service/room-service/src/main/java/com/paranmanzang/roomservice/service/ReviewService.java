package com.paranmanzang.roomservice.service;

import com.paranmanzang.roomservice.model.domain.ReviewModel;
import com.paranmanzang.roomservice.model.domain.ReviewUpdateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewModel insert(ReviewModel model);

    ReviewModel update(ReviewUpdateModel model);

    Boolean delete(Long id);

    Page<?> findByRoom(Long roomId, Pageable pageable);

    Page<?> findByUser(String nickname, Pageable pageable);
    ReviewModel findById(Long id);

    Page<?> findAll(Pageable pageable);
}
