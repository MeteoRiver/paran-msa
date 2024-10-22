package com.paranmanzang.roomservice.service.impl;

import com.paranmanzang.roomservice.model.domain.ReviewModel;
import com.paranmanzang.roomservice.model.domain.ReviewUpdateModel;
import com.paranmanzang.roomservice.model.entity.Review;
import com.paranmanzang.roomservice.model.repository.BookingRepository;
import com.paranmanzang.roomservice.model.repository.ReviewRepository;
import com.paranmanzang.roomservice.model.repository.RoomRepository;
import com.paranmanzang.roomservice.service.ReviewService;
import com.paranmanzang.roomservice.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
private final Converter converter;
    @Override
    public ReviewModel insert(ReviewModel model) {
        return converter.convertToReviewModel(reviewRepository.save(Review.builder()
                .content(model.getContent())
                .rating(model.getRating())
                .nickname(model.getNickname())
                .room(roomRepository.findById(model.getRoomId()).get())
                .booking(bookingRepository.findById(model.getBookingId()).get())
                .build()));
    }

    @Override
    @Transactional
    public ReviewModel update(ReviewUpdateModel model) {
        return reviewRepository.findById(model.getId()).map(review -> {
            review.setRating(model.getRating());
            review.setContent(model.getContent());
            return reviewRepository.save(review);
        }).map(converter::convertToReviewModel).orElse(null);
    }

    @Override
    public Boolean delete(Long id) {
        reviewRepository.delete(Review.builder().id(id).build());
        return reviewRepository.findById(id).isEmpty();
    }

    @Override
    public Page<?> findByRoom(Long roomId, Pageable pageable) {
        return reviewRepository.findByRoom(roomId, pageable);
    }

    @Override
    public Page<?> findByUser(String nickname, Pageable pageable) {
        return reviewRepository.findByUser(nickname, pageable);
    }

    @Override
    public ReviewModel findById(Long id) {
        return reviewRepository.findById(id).map(converter::convertToReviewModel).orElse(null);
    }

    @Override
    public Page<?> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }
}
