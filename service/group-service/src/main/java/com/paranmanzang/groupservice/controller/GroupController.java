package com.paranmanzang.groupservice.controller;

import com.paranmanzang.groupservice.model.domain.GroupModel;
import com.paranmanzang.groupservice.model.domain.JoiningModel;
import com.paranmanzang.groupservice.service.impl.GroupServiceImpl;
import com.paranmanzang.groupservice.service.impl.JoiningServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupServiceImpl groupService;
    private final JoiningServiceImpl joiningService;

    //(#61 Old Ver.) 전체 그룹 가져오기
    @GetMapping
    public ResponseEntity<?> findList(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(groupService.groupList(PageRequest.of(page, size)));
    }

    // 그룹에 해당하는 유저리스트 가져오기
    @GetMapping("/users/{groupId}")
    public ResponseEntity<?> findUserById(@PathVariable Long groupId) {
        return ResponseEntity.ok(joiningService.getUserListById(groupId));
    }

    // 소모임 만든 후 채팅방 개설 후 실행
    @PutMapping("/chat-room/{groupId}")
    public ResponseEntity<?> updateChatRoomId(@RequestBody String roomId, @PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.updateChatRoomId(roomId, groupId));
    }

    //#61. 참여중인 소모임 조회
    @GetMapping("/my-groups")
    public ResponseEntity<?> findByNickname(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(groupService.groupsByUserNickname(nickname));
    }

    //#63.소모임 등록
    @PostMapping //request: groupname, groupconcept
    public ResponseEntity<?> insert(@Valid @RequestBody GroupModel groupModel,
                                      BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return ResponseEntity.ok(groupService.addGroup(groupModel));
    }

    //#64.소모임 등록 승인
    @Transactional
    @PutMapping("/able")
    public ResponseEntity<?> able(@RequestParam Long groupId) {
        return ResponseEntity.ok(groupService.enableGroup(groupId));
    }

    //#66.소모임 멤버 추가
    @PostMapping("/user")//request: userNickname, groupId
    public ResponseEntity<?> insertUser(@Valid @RequestBody JoiningModel joiningModel,
                                       BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return ResponseEntity.ok(joiningService.addMember(joiningModel));
    }

    //#66-1.소모임 멤버 승인
    //처리 후 바로 채팅방 입장

    @Transactional
    @PutMapping("/able-user")
    public ResponseEntity<?> ableUser(@RequestParam("groupId") Long groupId,
                                               @RequestParam("nickname") String userNickname) {
        return ResponseEntity.ok(joiningService.enableMember(groupId, userNickname));
    }
    //#66-2.소모임 멤버승인 취소

    @Transactional
    @PutMapping("/enable-user")
    public ResponseEntity<?> enableUser(@RequestParam("groupId") Long groupId,
                                                @RequestParam("nickname") String userNickname) {
        return ResponseEntity.ok(joiningService.disableMember(groupId, userNickname));
    }
    //#64-1.소모임 삭제

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(groupService.deleteGroup(groupId));
    }

    //#64.소모임 승인 취소
    @Transactional
    @PutMapping("/enable")
    public ResponseEntity<?> enable(@RequestParam Long groupId) {
        return ResponseEntity.ok(groupService.enableCancelGroup(groupId));
    }

    // 승인해야 하는 소모임 리스트
    @GetMapping("/enable-list")
    public ResponseEntity<?> enableList(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(groupService.enableGroupList(PageRequest.of(page, size)));
    }

    @DeleteMapping("/users/{groupId}")
    public ResponseEntity<?> deleteUser(@RequestBody String nickname, @PathVariable Long groupId) {
        return ResponseEntity.ok(joiningService.deleteUser(nickname, groupId));
    }

}
