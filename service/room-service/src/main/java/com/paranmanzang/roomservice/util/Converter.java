package com.paranmanzang.roomservice.util;

import com.paranmanzang.roomservice.model.domain.*;
import com.paranmanzang.roomservice.model.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class Converter {
    public AddressModel convertTonAddressModel(Address address){
        return AddressModel.builder()
                .address(address.getAddress())
                .detailAddress(address.getDetailAddress())
                .longitude(address.getLongitude())
                .latitude(address.getLatitude())
                .id(address.getId())
                .roomId(address.getRoomId())
                .build();
    }
    public BookingModel convertToBookingModel(Booking booking){
        return BookingModel.builder()
                .id(booking.getId())
                .date(booking.getDate())
                .enabled(booking.isEnabled())
                .usingTime(
                        Optional.ofNullable(booking.getTimes())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Time::getTime)
                        .toList())
                .roomId(booking.getRoom().getId())
                .groupId(booking.getGroupId())
                .build();
    }

    public ReviewModel convertToReviewModel(Review review){
        return ReviewModel.builder()
                .id(review.getId())
                .bookingId(review.getBooking().getId())
                .content(review.getContent())
                .createAt(review.getCreateAt())
                .nickname(review.getNickname())
                .rating(review.getRating())
                .roomId(review.getRoom().getId())
                .build();
    }

    public RoomModel convertToRoomModel(Room room){
        return RoomModel.builder()
                .name(room.getName())
                .closeTime(room.getCloseTime())
                .price(room.getPrice())
                .createdAt(room.getCreatedAt())
                .enabled(room.isEnabled())
                .id(room.getId())
                .maxPeople(room.getMaxPeople())
                .nickname(room.getNickname())
                .opened(room.isOpened())
                .openTime(room.getOpenTime())
                .build();
    }
    public TimeModel convertToTimeModel(Time time){
        return TimeModel.builder()
                .time(time.getTime().toString())
                .date(time.getDate().toString())
                .id(time.getId())
                .build();
    }
}
