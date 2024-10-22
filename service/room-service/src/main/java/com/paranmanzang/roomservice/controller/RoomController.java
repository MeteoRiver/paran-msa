
package com.paranmanzang.roomservice.controller;

import com.paranmanzang.roomservice.model.domain.RoomModel;
import com.paranmanzang.roomservice.model.domain.RoomUpdateModel;
import com.paranmanzang.roomservice.service.impl.RoomServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "01. Room")
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomServiceImpl roomService;

    @PostMapping("")
    @Operation(summary = "공간 등록", description = "공간정보를 db에 저장합니다.", tags = {"01. Room",})
    public ResponseEntity<?> insert(@Valid @RequestBody RoomModel model, BindingResult result)
            throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(roomService.insert(model));
    }

    @PutMapping("")
    @Operation(summary="공간 수정", description = "공간정보를 수정합니다.")
    public ResponseEntity<?> update(@Valid @RequestBody RoomUpdateModel model, BindingResult result)
            throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(roomService.update(model));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "공간 삭제", description = "id 값에 해당하는 공간정보를 삭제합니다.")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roomService.delete(id));
    }

    @GetMapping("/user/{nickname}")
    @Operation(summary = "등록자의 모든 공간 조회", description = "nickname인 유저가 등록한 모든 공간정보를 조회합니다.")
    public ResponseEntity<?> findAllByUser(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(roomService.findAllByNickname(nickname));
    }
    @GetMapping("/user")
    @Operation(summary = "등록자의 공간 조회", description = "nickname인 유저가 등록한  공간정보를 조회합니다. pagination")
    public ResponseEntity<?> findByUser(@RequestParam String nickname, Pageable pageable) {
        return ResponseEntity.ok(roomService.findByNickname(nickname, pageable));
    }

    @GetMapping("")
    @Operation(summary = "공간 조회", description = "등록된 모든 공간정보를 조회합니다.")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return ResponseEntity.ok(roomService.findAll(pageable));
    }

    @GetMapping("/enabled-all")
    @Operation(summary = "승인된 공간 조회", description = "승인된 모든 공간정보를 조회합니다.")
    public ResponseEntity<?> findAllByEnabled( ) {
        return ResponseEntity.ok(roomService.findAllByEnabled());
    }

    @GetMapping("/enabled")
    @Operation(summary = "승인된 공간 조회", description = "승인된 공간정보를 조회합니다. '페이지네이션'")
    public ResponseEntity<?> findByEnabled(Pageable pageable) {
        return ResponseEntity.ok(roomService.findByEnabled(pageable));
    }

    @GetMapping("/like/{nickname}")
    @Operation(summary = "좋아요한 공간 조회", description = "nickname인 유저가 좋아요한 공간정보를 조회합니다.")
    public ResponseEntity<?> findByLikeRoom(@PathVariable String nickname){
        return ResponseEntity.ok(roomService.findByLikeRoom(nickname));
    }

    @PutMapping("/confirm/{id}")
    @Operation(summary = "공간 승인", description = "공간 등록이 승인되어 정보가 수정됩니다.")
    public ResponseEntity<?> confirm(@PathVariable() Long id) {
        return ResponseEntity.ok(roomService.confirm(id));
    }

}

