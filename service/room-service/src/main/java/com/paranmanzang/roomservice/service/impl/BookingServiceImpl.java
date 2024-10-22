package com.paranmanzang.roomservice.service.impl;

import com.paranmanzang.roomservice.model.domain.BookingModel;
import com.paranmanzang.roomservice.model.entity.Booking;
import com.paranmanzang.roomservice.model.entity.Room;
import com.paranmanzang.roomservice.model.entity.Time;
import com.paranmanzang.roomservice.model.repository.BookingRepository;
import com.paranmanzang.roomservice.model.repository.RoomRepository;
import com.paranmanzang.roomservice.service.BookingService;
import com.paranmanzang.roomservice.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final TimeServiceImpl timeService;
    private final AccountServiceImpl accountService;
    private final Converter converter;

    @Override
    public BookingModel insert(BookingModel model) {
       return Optional.of(bookingRepository.save(Booking.builder()
                        .date(model.getDate())
                        .groupId(model.getGroupId())
                        .createAt(LocalDateTime.now())
                        .room(roomRepository.findById(model.getRoomId()).get())
                        .build()))
                .map(booking -> {
                    model.setId(booking.getId());
                    timeService.saveBooking(model);
                    return booking;
                }).map(converter::convertToBookingModel).map(bookingModel -> {
                   if(bookingModel.getUsingTime().isEmpty()) bookingModel.setUsingTime(timeService.findByBooking(bookingModel.getId()));
                   return bookingModel;
               }).get();
    }

    @Override
    public Boolean delete(Long id) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    BookingModel bookingModel = converter.convertToBookingModel(booking);
                    bookingRepository.delete(booking);
                    return timeService.saveBooking(bookingModel) && accountService.cancel(id);
                })
                .orElse(false);
    }

    @Override
    public Page<?> findByGroup(long groupId, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByGroupId(groupId, pageable);

        return new PageImpl<>(bookings.stream()
                .map(converter::convertToBookingModel)
                .toList(), pageable, bookings.getTotalElements());
    }


    @Override
    public Page<?> findByRoom(long roomId, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByRoomId(roomId, pageable);
        return new PageImpl<>(bookings.stream()
                .map(converter::convertToBookingModel)
                .toList(), pageable, bookings.getTotalElements());
    }

    @Override
    public BookingModel findOne(long id) {
        return bookingRepository.findById(id).map(booking ->
                new BookingModel(booking.getId(), booking.isEnabled(), booking.getDate(), booking.getTimes().stream().map(Time::getTime).toList(), booking.getRoom().getId(), booking.getGroupId())
        ).orElse(null);
    }

    @Override
    public Page<?> findByGroups(List<Long> groupIds, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByGroupIds(groupIds, pageable);
        return new PageImpl<>(bookings.stream().map(converter::convertToBookingModel).toList(), pageable, bookings.getTotalElements());
    }

    @Override
    public Page<?> findByRooms(String nickname, Pageable pageable) {
        Page<Booking> bookings= bookingRepository.findByRoomIds(roomRepository.findAllByNickname(nickname).stream().map(Room::getId).toList(), pageable);

        return new PageImpl<>(bookings.stream().map(converter::convertToBookingModel).toList(), pageable, bookings.getTotalElements());
    }


    @Override
    public BookingModel updateState(Long id) {
        if (bookingRepository.findById(id).get().isEnabled()) return null;

        return bookingRepository.findById(id).map(booking -> {
                    booking.setEnabled(true);
                    booking.setResponseAt(LocalDateTime.now());
                    return bookingRepository.save(booking);
                })
                .map(converter::convertToBookingModel).get();
    }

}
