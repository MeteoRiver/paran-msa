package com.paranmanzang.roomservice.controller;

import com.paranmanzang.roomservice.model.domain.ReviewModel;
import com.paranmanzang.roomservice.model.domain.ReviewUpdateModel;
import com.paranmanzang.roomservice.service.impl.ReviewServiceImpl;
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
@Tag(name = "04. Review")
@RequestMapping("/api/rooms/reviews")
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    @PostMapping("")
    @Operation(summary = "리뷰 등록", description = "리뷰를 db에 저장합니다.", tags = {"04. Review",})
    public ResponseEntity<?> insert(@Valid  @RequestBody ReviewModel reviewModel, BindingResult result) throws BindException {
        if (result.hasErrors()) throw new BindException(result);
        return ResponseEntity.ok(reviewService.insert(reviewModel));
    }

    @PutMapping("")
    @Operation(summary = "리뷰 수정",description = "리뷰 정보를 수정합니다.")
    public ResponseEntity<?> update(@Valid @RequestBody ReviewUpdateModel reviewModel, BindingResult result) throws BindException{
        if (result.hasErrors()) throw new BindException(result);
        return ResponseEntity.ok(reviewService.update(reviewModel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "리뷰 삭제", description = "id 값에 해당하는 리뷰를 삭제합니다.")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
        return ResponseEntity.ok(reviewService.delete(id));
    }

    @GetMapping("")
    @Operation(summary = "리뷰 조회", description = "존재하는 모든 리뷰정보를 조회합니다.")
    public ResponseEntity<?> findAll(Pageable pageable){
        return ResponseEntity.ok(reviewService.findAll(pageable));
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "공간 기준 조회", description = "roomId에 해당하는 모든 리뷰정보를 조회합니다.")
    public ResponseEntity<?> findByByRoom(@PathVariable("roomId") Long roomId, Pageable pageable){
        return ResponseEntity.ok(reviewService.findByRoom(roomId, pageable));
    }
    @GetMapping("/user/{roomId}")
    @Operation(summary = "공간 기준 조회", description = "roomId에 해당하는 모든 리뷰정보를 조회합니다.")
    public ResponseEntity<?> findByUser(@RequestParam String nickname, Pageable pageable){
        return ResponseEntity.ok(reviewService.findByUser(nickname, pageable));
    }
}
