package com.paranmanzang.roomservice.service.impl;

import com.paranmanzang.roomservice.model.domain.*;
import com.paranmanzang.roomservice.model.entity.Room;
import com.paranmanzang.roomservice.model.repository.RoomRepository;
import com.paranmanzang.roomservice.service.RoomService;
import com.paranmanzang.roomservice.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final LikeRoomServiceImpl likeRoomService;
    private final TimeServiceImpl timeService;
    private final Converter converter;

    @Override
    public RoomModel insert(RoomModel model) {
        return converter.convertToRoomModel(roomRepository.save(Room.builder()
                .name(model.getName())
                .price(model.getPrice())
                .maxPeople(model.getMaxPeople())
                .opened(model.isOpened())
                .openTime(model.getOpenTime())
                .closeTime(model.getCloseTime())
                .nickname(model.getNickname())
                .build()));
    }

    @Override
    public RoomModel update(RoomUpdateModel model) {
        return roomRepository.findById(model.getId()).map(room -> {
                    room.setName(model.getName());
                    room.setMaxPeople(model.getMaxPeople());
                    room.setPrice(model.getPrice());
                    room.setOpened(model.isOpened());
                    room.setOpenTime(model.getOpenTime());
                    room.setCloseTime(model.getCloseTime());
                    return roomRepository.save(room);
                }).map(converter::convertToRoomModel)
                .orElse(null);
    }

    @Override
    public Boolean delete(Long id) {
        return roomRepository.findById(id)
                .map(room -> {
                    roomRepository.delete(room);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public RoomModel confirm(Long id) {
        return roomRepository.findById(id)
                .filter(room -> !room.isEnabled())
                .map(room -> {
                    room.setEnabled(true);
                    room.setResponseAt(LocalDateTime.now());
                    Room savedRoom = roomRepository.save(room);
                    timeService.saveList(
                            TimeSaveModel.builder()
                                    .roomId(savedRoom.getId())
                                    .openTime(savedRoom.getOpenTime().getHour())
                                    .closeTime(savedRoom.getCloseTime().getHour())
                                    .build());
                    return converter.convertToRoomModel(savedRoom);
                })
                .orElse(null);
    }


    @Override
    public Page<?> findAll(Pageable pageable) {
        return roomRepository.findByPagination(pageable);
    }

    @Override
    public Page<RoomModel> findByEnabled(Pageable pageable) {
        return roomRepository.findByPagination(pageable);
    }

    @Override
    public List<Long> getIdAllEnabled() {
        return roomRepository.findAll().stream().filter(Room::isEnabled).map(Room::getId).toList();
    }

    @Override
    public Page<?> findByNickname(String nickname, Pageable pageable) {
        return roomRepository.findByNickname(nickname, pageable);
    }

    @Override
    public RoomWTimeModel findByIdWithTime(Long id) {
        return roomRepository.findById(id).filter(Room::isEnabled).map(room ->
                new RoomWTimeModel(room.getId(), room.getName(), room.getMaxPeople(), room.getPrice(), room.isOpened(),
                        room.getOpenTime(), room.getCloseTime(), room.getCreatedAt(), room.isEnabled(), room.getNickname(),
                        room.getTimes().stream().filter(time -> time.getDate().isAfter(LocalDate.now()))
                                .filter(time -> !time.isEnabled())
                                .map(converter::convertToTimeModel).toList()
                )
        ).orElse(null);
    }

    @Override
    public List<?> findByLikeRoom(String nickname) {
        return likeRoomService.findAllByUserNickname(nickname).stream()
                .map(LikeRoomModel::getRoomId)
                .map(roomRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .map(converter::convertToRoomModel)
                .toList();

    }

    @Override
    public List<?> findAllByEnabled() {
        return roomRepository.findAll().stream().filter(Room::isEnabled).map(converter::convertToRoomModel).toList();
    }

    @Override
    public List<?> findAllByNickname(String nickname) {
        return roomRepository.findAllByNickname(nickname).stream().map(converter::convertToRoomModel).toList();
    }

    @Override
    public RoomModel findById(Long id) {
        return roomRepository.findById(id).map(converter::convertToRoomModel).orElse(null);
    }

}