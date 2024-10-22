package com.paranmanzang.roomservice.controller;

import com.paranmanzang.roomservice.service.impl.TimeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "06. Time")
@RequestMapping("/api/rooms/times")
public class TimeController {
    private final TimeServiceImpl timeService;

    @GetMapping("/{roomId}")
    @Operation(summary = "예약 가능 시간 조회", description = "해당 공간에 대한 예약 가능한 시간을 조회합니다.", tags = {"06. Time",})
    public ResponseEntity<?> getTimeList(@PathVariable("roomId") long roomId){
        return ResponseEntity.ok(timeService.findByRoom(roomId));
    }
}
