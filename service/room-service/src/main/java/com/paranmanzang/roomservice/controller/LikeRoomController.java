package com.paranmanzang.roomservice.controller;

import com.paranmanzang.roomservice.model.domain.LikeRoomModel;
import com.paranmanzang.roomservice.service.impl.LikeRoomServiceImpl;
import com.paranmanzang.roomservice.service.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms/likerooms")
@RequiredArgsConstructor
public class LikeRoomController {
    private final LikeRoomServiceImpl likeRoomService;
    private final RoomServiceImpl roomService;

    //좋아요
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody LikeRoomModel likeRoomModel) {
        return ResponseEntity.ok(likeRoomService.insert(likeRoomModel));
    }

    //좋아요 취소
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody LikeRoomModel likeRoomModel) {
        return ResponseEntity.ok(likeRoomService.remove(likeRoomModel));
    }

}
