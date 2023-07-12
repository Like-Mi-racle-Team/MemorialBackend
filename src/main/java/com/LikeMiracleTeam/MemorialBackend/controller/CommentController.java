package com.LikeMiracleTeam.MemorialBackend.controller;

import com.LikeMiracleTeam.MemorialBackend.dto.request.CommentRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.CommentResponse;
import com.LikeMiracleTeam.MemorialBackend.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    public ResponseEntity<Page<CommentResponse>> getComments(
            @ApiParam(value = "찾을 댓글이 쓰인 게시물의 postNo") @PathVariable(name = "postNo")Long postNo,
            @ApiParam(value = "페이징 파라미터") Pageable pageable) {
        return commentService.getComments(postNo, pageable);
    }

    @PostMapping("/{postNo}")
    @ApiResponse(code = 404, message = "게시물이 존재치 않음")
    public ResponseEntity<CommentResponse> postComment(
            @ApiParam(value = "엑세스 토큰")@RequestHeader(name = "Authorization")String tokenString,
            @ApiParam(value = "댓글을 쓸 게시물의 postNo") @PathVariable(name = "postNo")Long postNo,
                                                       @RequestBody CommentRequest request) {
        return commentService.postComment(tokenString, postNo, request);
    }

    @PatchMapping("/{commentNo}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "게시물이 존재치 않음"),
            @ApiResponse(code = 403, message = "인증안됨, 작성자가 아님")
    })
    public ResponseEntity<CommentResponse> updateComment(
            @ApiParam(value = "엑세스 토큰") @RequestHeader(name = "Authorization")String tokenString,
            @ApiParam(value = "수정할 댓글의 commentNo") @PathVariable(name = "commentNo")Long commentNo,
                                                         @RequestBody CommentRequest request) {
        return commentService.updateComment(tokenString, commentNo, request);
    }

    @DeleteMapping("/{commentNo}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "게시물이 존재치 않음"),
            @ApiResponse(code = 403, message = "인증안됨, 작성자가 아님")
    })
    public ResponseEntity<CommentResponse> deleteComment(
            @ApiParam(value = "엑세스 토큰")@RequestHeader(name = "Authorization")String tokenString,
            @ApiParam(value = "삭제할 댓글의 commentNo") @PathVariable(name = "commentNo")Long commentNo) {
        return commentService.deleteComment(tokenString, commentNo);
    }

}
