package com.paranmanzang.groupservice.controller;


import com.paranmanzang.groupservice.model.domain.LikePostModel;
import com.paranmanzang.groupservice.service.impl.LikePostServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/groups/like-post")
public class LikePostController {

    private final LikePostServiceImpl likePostService;

    public LikePostController(LikePostServiceImpl likePostService) {
        this.likePostService = likePostService;
    }

    //좋아요
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody LikePostModel likepostModel) {
        return ResponseEntity.ok(likePostService.insert(likepostModel));
    }
    //좋아요 취소
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody LikePostModel likepostModel) {
        return ResponseEntity.ok(likePostService.remove(likepostModel));
    }


    //좋아요 마이페이지 확인
    @GetMapping("/{nickname}")
    public ResponseEntity<?> findAllByNickname(@PathVariable String nickname) {
        System.out.println("nickname = " + nickname);
        return ResponseEntity.ok(likePostService.findAllByNickname(nickname));
    }
}
