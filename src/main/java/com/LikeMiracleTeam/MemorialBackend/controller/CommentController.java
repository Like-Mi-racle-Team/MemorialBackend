package com.LikeMiracleTeam.MemorialBackend.controller;

import com.LikeMiracleTeam.MemorialBackend.dto.request.CommentRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.CommentResponse;
import com.LikeMiracleTeam.MemorialBackend.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postNo}")
    public ResponseEntity<Page<CommentResponse>> getComments(@PathVariable(name = "postNo")Long postNo,
                                                             Pageable pageable) {
        return commentService.getComments(postNo, pageable);
    }

    @PostMapping("/{postNo}")
    public ResponseEntity<CommentResponse> postComment(@RequestHeader(name = "Authorization")String tokenString,
                                                       @PathVariable(name = "postNo")Long postNo,
                                                       @RequestBody CommentRequest request) {
        return commentService.postComment(tokenString, postNo, request);
    }

    @PatchMapping("/{commentNo}")
    public ResponseEntity<CommentResponse> updateComment(@RequestHeader(name = "Authorization")String tokenString,
                                                         @PathVariable(name = "commentNo")Long commentNo,
                                                         @RequestBody CommentRequest request) {
        return commentService.updateComment(tokenString, commentNo, request);
    }

    @DeleteMapping("/{commentNo}")
    public ResponseEntity<CommentResponse> deleteComment(@RequestHeader(name = "Authorization")String tokenString,
                                                         @PathVariable(name = "commentNo")Long commentNo) {
        return commentService.deleteComment(tokenString, commentNo);
    }

}
