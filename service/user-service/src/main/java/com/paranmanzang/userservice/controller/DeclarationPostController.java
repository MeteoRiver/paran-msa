package com.paranmanzang.userservice.controller;

import com.paranmanzang.userservice.model.domain.DeclarationPostModel;
import com.paranmanzang.userservice.service.impl.DeclarationPostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "02. DeclarationPost")
@RequestMapping("/api/users/depost")
public class DeclarationPostController {

    @Autowired
    private DeclarationPostServiceImpl declarationPostService;

    public DeclarationPostController(DeclarationPostServiceImpl declarationPostService) {
        this.declarationPostService = declarationPostService;
    }

    //신고 게시글 작성
    @PostMapping
    @Operation(summary ="신고 게시글 작성", description = "게시글 정보를 저장합니다.", tags = {"02. DeclarationPost"})
    public ResponseEntity<?> insert(@RequestBody DeclarationPostModel declarationPostModel) {
        return ResponseEntity.ok( declarationPostService.insert(declarationPostModel));
    }
    //신고 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        return ResponseEntity.ok( declarationPostService.remove(id));
    }

    //신고 게시글 조회 (관리자만)
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam int page, @RequestParam int size) {
        return  ResponseEntity.ok(declarationPostService.findAll(PageRequest.of(page,size)));
    }
    //신고 게시글 조회 (본인거)
    @GetMapping("/{nickname}")
    @Operation(summary = "신고 게시글 조회", description = "신고 게시글을 조회합니다. 관리자는 모든 게시물을 조회할 수 있으며, 글을 쓴 작성자는 본인의 게시물만을 확인할 수 있습니다.")
    public ResponseEntity<?> findAllByNickname(@PathVariable String nickname, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(declarationPostService.findAllByNickname(nickname, PageRequest.of(page,size)));
    }

    //신고 게시글 상세 조회 (관리자 이거나 신고자 본인만)
    @GetMapping("/details/{postId}")
    @Operation(summary = "신고 게시글 상세 조회", description = "신고 게시글을 상세조회합니다. 관리자는 모든 게시물을 상세조회 할 수 있으며, 글을 쓴 작성자는 본인의 게시글만 상세조회 할 수 있습니다.")
    public ResponseEntity<?> findByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(declarationPostService.findByPostId(postId));
    }
}
