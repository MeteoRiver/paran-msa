package com.paranmanzang.roomservice.controller;

import com.paranmanzang.roomservice.model.domain.BookingModel;
import com.paranmanzang.roomservice.service.impl.BookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "02. Booking")
@RequestMapping("/api/rooms/bookings")
public class BookingController {
    private final BookingServiceImpl bookingService;

    @PostMapping("")
    @Operation(summary = "예약 등록", description = "예약정보를 db에 저장합니다.", tags = {"02. Booking", })
    public ResponseEntity<?> insert(@Valid @RequestBody BookingModel model, BindingResult result)
            throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(bookingService.insert(model));
    }

    @PutMapping("/{id}")
    @Operation(summary = "예약 승인", description = "예약이 승인되어 정보가 수정됩니다.")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookingService.updateState(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "예약 취소", description = "예약 정보가 삭제됩니다.")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookingService.delete(id));
    }

    @GetMapping("/group")
    @Operation(summary = "소모임 예약 조회", description = "해당 소모임에 대한 모든 예약정보를 조회합니다")
    public ResponseEntity<?> findByGroups(@RequestParam("groupIds") List<Long> groupIds, Pageable pageable) {
        return ResponseEntity.ok(bookingService.findByGroups(groupIds, pageable));
    }
    @GetMapping("/group/{groupId}")
    @Operation(summary = "소모임 예약 조회", description = "해당 소모임에 대한 모든 예약정보를 조회합니다")
    public ResponseEntity<?> findByGroup(@PathVariable("groupId") long groupId, Pageable pageable) {
        return ResponseEntity.ok(bookingService.findByGroup(groupId, pageable));
    }

    @GetMapping("/room")
    @Operation(summary = "공간 예약 조회", description = "해당 공간에 대한 모든 예약정보를 조회합니다.")
    public ResponseEntity<?> findByRoom(@RequestParam("nickname") String nickname, Pageable pageable) {
        return ResponseEntity.ok(bookingService.findByRooms(nickname, pageable));
    }
    @GetMapping("/room/{roomId}")
    @Operation(summary = "공간 예약 조회", description = "해당 공간에 대한 모든 예약정보를 조회합니다.")
    public ResponseEntity<?> findByRoom(@PathVariable("roomId") long roomId, Pageable pageable) {
        return ResponseEntity.ok(bookingService.findByRoom(roomId, pageable));
    }
}
