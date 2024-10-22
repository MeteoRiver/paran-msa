package com.paranmanzang.commentservice.controller;

import com.paranmanzang.commentservice.model.domain.CommentRequestModel;
import com.paranmanzang.commentservice.service.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid CommentRequestModel model, @RequestHeader String nickname, BindingResult result)
            throws BindException {
        if (result.hasErrors()) throw new BindException(result);
        return ResponseEntity.ok(commentService.insert(model, nickname));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.delete(commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> update(@PathVariable Long commentId, @RequestBody String content
            , @RequestHeader String nickname) {
        return ResponseEntity.ok(commentService.update(commentId, content, nickname));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> findByPostId(@PathVariable Long postId, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(commentService.getCommentListByPostId(postId, PageRequest.of(page, size)));
    }

}
