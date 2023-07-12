package com.LikeMiracleTeam.MemorialBackend.controller;

import com.LikeMiracleTeam.MemorialBackend.dto.request.PostRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.PostResponse;
import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import com.LikeMiracleTeam.MemorialBackend.service.FileService;
import com.LikeMiracleTeam.MemorialBackend.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final FileService fileService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getRecommendPosts() {
        return postService.getRecommendPosts();
    }

    @GetMapping("/{postNo}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "게시물이 존재치 않음")
    })
    public ResponseEntity<PostResponse> getPost( @ApiParam(value = "조회할 게시물의 postNo") @PathVariable(name = "postNo")Long postNo) {
        return postService.getPost(postNo);
    }

    @GetMapping(path = "/{postNo}/file", produces = MediaType.IMAGE_PNG_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "게시물이 존재치 않음"),
            @ApiResponse(code = 500, message = "파일 업로드 오류, 혹은 예상치 못한 오류 발생")
    })
    public ResponseEntity<InputStreamResource> getPostFile(@ApiParam(value = "조회할 게시물의 postNo") @PathVariable(name = "postNo")Long postNo) {
        try {
            return fileService.downloadFile(postNo);
        } catch (IOException e) {
            log.info(String.valueOf(e.getCause()));
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ApiResponse(code = 500, message = "파일 업로드 오류, 혹은 예상치 못한 오류 발생")
    public ResponseEntity<PostResponse> createPost(
            @ApiParam(value = "엑세스 토큰")@RequestHeader(name = "Authorization")String tokenString,
            @RequestPart(name = "dto") PostRequest request,
            @RequestPart(name = "file")MultipartFile file) {
        return postService.createPost(tokenString, request, file);
    }

    @PatchMapping("/{postNo}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "게시물이 존재치 않음"),
            @ApiResponse(code = 403, message = "인증안됨, 작성자가 아님"),
            @ApiResponse(code = 500, message = "파일 업로드 오류, 혹은 예상치 못한 오류 발생")
    })
    public ResponseEntity<PostResponse> updatePost(
            @ApiParam(value = "엑세스 토큰")@RequestHeader(name = "Authorization")String tokenString,
            @RequestPart(name = "dto") PostRequest request,
            @RequestPart(name = "file") MultipartFile file,
            @ApiParam(value = "수정할 게시물의 postNo") @PathVariable(name = "postNo")Long postNo) {
        return postService.updatePost(tokenString, request, file, postNo);
    }

    @DeleteMapping("/{postNo}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "게시물이 존재치 않음"),
            @ApiResponse(code = 403, message = "인증안됨, 작성자가 아님")
    })
    public ResponseEntity<PostResponse> deletePost(
            @ApiParam(value = "엑세스 토큰") @RequestHeader(name = "Authorization")String tokenString,
                                                   @PathVariable(name = "postNo")Long postNo){
        return postService.deletePost(tokenString, postNo);
    }

    @PostMapping("/{postNo}/like")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "게시물이 존재치 않음")
    })
    public ResponseEntity<Void> like(@RequestHeader(name = "Authorization")String tokenString,
                                           @PathVariable(name = "postNo")Long postNo){
        return postService.like(tokenString, postNo);
    }

    @DeleteMapping("/{postNo}/like")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "게시물이 존재치 않음, 좋아요 표시를 하지 않음")
    })
    public ResponseEntity<Void> cancelLike(@RequestHeader(name = "Authorization")String tokenString,
                                                   @PathVariable(name = "postNo")Long postNo){
        return postService.cancelLike(tokenString, postNo);
    }

}
