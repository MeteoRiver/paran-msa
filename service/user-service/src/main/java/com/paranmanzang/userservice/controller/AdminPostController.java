package com.paranmanzang.userservice.controller;

import com.paranmanzang.userservice.model.domain.AdminPostModel;
import com.paranmanzang.userservice.model.entity.AdminPosts;
import com.paranmanzang.userservice.service.impl.AdminPostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Tag(name = "01. AdminPost")
@RequestMapping("/api/users/aboard")
public class AdminPostController {

    private final AdminPostServiceImpl adminPostService;

    public AdminPostController(AdminPostServiceImpl adminPostService) {
        this.adminPostService = adminPostService;
    }

    // 게시글 작성
    @PostMapping
    @Operation(summary = "게시글 작성", description = "입력된 게시물 정보를 저장합니다.", tags = {"01. AdminPost"})
    public ResponseEntity<?> insert(@RequestBody AdminPostModel adminPostModel) {
        return ResponseEntity.ok(adminPostService.insert(adminPostModel));
    }
    // 게시글 수정
    @PutMapping("/{id}")
    @Operation(summary = "게시물 수정", description = "입력된 게시물 정보로 입력받은 게시물 번호의 게시물을 수정합니다")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AdminPostModel adminPostModel) {
        return ResponseEntity.ok(adminPostService.update(id, adminPostModel));
    }
    // 게시글 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "게시물 삭제", description = "게시물 번호를 받고 해당 게시물을 삭제합니다.")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        return ResponseEntity.ok(adminPostService.remove(id));
    }
    // 게시글 조회
    @GetMapping
    @Operation(summary = "게시물 조회", description = "게시물 리스트를 조회합니다.")
    public ResponseEntity<?> findAll(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(adminPostService.findAll(PageRequest.of(page,size)));
    }
    //게시글 마이페이지 조회
    @GetMapping("/{nickname}")
    @Operation(summary = "관리자 본인 게시물 조회", description = "게시물 리스트를 조회합니다.")
    public ResponseEntity<?> findAllByNickname(@PathVariable String nickname, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(adminPostService.findAllByNickname(nickname, PageRequest.of(page,size)));
    }

    // 게시글 상세 조회
    @GetMapping("/details/{id}")
    @Operation(summary = "게시물 상세조회", description = "게시물 번호를 받고 해당 게시물을 조회합니다.")
    public ResponseEntity<AdminPosts> findByAdminPostId(@PathVariable Long id) {
        return ResponseEntity.ok(adminPostService.findByAdminPostId(id));
    }

    // 게시글 조회수 확인
    @GetMapping("/viewCounts/{id}")
    @Operation(summary = "게시물 조회수 조회", description = "게시물 번호를 받고 해당 게시물의 조회수를 조회합니다. 해당 조회수는 게시글 상세조회가 실행될때마다 올라갑니다.")
    public ResponseEntity<Long> findViewCountById(@PathVariable Long id) {
        return ResponseEntity.ok(adminPostService.findViewCountById(id));
    }
}
