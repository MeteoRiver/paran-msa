package com.paranmanzang.userservice.controller;


import com.paranmanzang.userservice.model.domain.FriendModel;
import com.paranmanzang.userservice.service.impl.FriendServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/friends")
public class FriendController {
    private final FriendServiceImpl friendService;

    public FriendController(FriendServiceImpl friendService) {
        this.friendService = friendService;
    }

    //친구 삭제 O
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        return ResponseEntity.ok(friendService.remove(id));
    }

    //친구 목록 조회 O
    @GetMapping("/{nickname}")
    public ResponseEntity<?> findAllByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(friendService.findAllByNickname(nickname));
    }
    //친구 요청 O
    @PostMapping("/request")
    public ResponseEntity<?> insert(@RequestBody FriendModel friendModel) {
        return ResponseEntity.ok(friendService.insertRequest(friendModel));
    }

    //친구 요청 목록 조회 O
    @GetMapping("/request/{nickname}")
    public ResponseEntity<?> findAllRequestByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(friendService.findAllRequestByNickname(nickname));
    }

    //친구 요청 수락 X
    @PostMapping("/state")
    public ResponseEntity<?> state(@RequestBody FriendModel friendModel, @RequestParam Boolean state) {
        return ResponseEntity.ok(friendService.state(friendModel, state));
    }
}

