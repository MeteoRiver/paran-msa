package com.paranmanzang.roomservice.controller;

import com.paranmanzang.roomservice.model.domain.AccountCancelModel;
import com.paranmanzang.roomservice.model.domain.AccountResultModel;
import com.paranmanzang.roomservice.service.impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "03. Account")
@RequestMapping("/api/rooms/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    @PostMapping("")
    @Operation(summary = "결제 등록", description = "완료된 결제 정보를 db에 저장합니다.", tags = {"03. Account",})
    public ResponseEntity<?> insert(@RequestBody AccountResultModel model) {
        return ResponseEntity.ok(accountService.insert(model));
    }

    @GetMapping("")
    @Operation(summary = "결제번호 조회", description = "주문 번호에 따른 paymentKey 값을 조회합니다.")
    public ResponseEntity<?> findByOrderId(@RequestParam String orderId){
        return ResponseEntity.ok(accountService.findByOrderId(orderId));
    }


    @PutMapping("")
    @Operation(summary = "결제 취소", description = "결제 취소 정보를 업데이트 합니다.")
    public ResponseEntity<?> update(@RequestBody AccountCancelModel model){
        return ResponseEntity.ok(accountService.cancel(model));
    }

    @GetMapping("/{bookingId}")
    @Operation(summary = "단일 결제 조회", description = "예약 정보에 따른 1건의 결제정보를 조회합니다.")
    public ResponseEntity<?> findByBooking(@PathVariable("bookingId") Long bookingId){
        return ResponseEntity.ok(accountService.findByBookingId(bookingId));
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "소모임 결제 조회", description="groupId에 해당하는 모든 결제정보를 조회합니다.")
    public ResponseEntity<?> findByGroup(@PathVariable("groupId") Long groupId, Pageable pageable){
        return ResponseEntity.ok(accountService.findByGroupId(groupId, pageable));
    }
    @GetMapping("/room/{roomId}")
    @Operation(summary = "공간 결제 조회", description="roomId에 해당하는 모든 결제정보를 조회합니다.")
    public ResponseEntity<?> findByRoom(@PathVariable("roomId") Long roomId, Pageable pageable){
        return ResponseEntity.ok(accountService.findByRoomId(roomId, pageable));
    }
}
