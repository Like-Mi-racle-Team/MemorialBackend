package com.LikeMiracleTeam.MemorialBackend.controller;

import com.LikeMiracleTeam.MemorialBackend.dto.request.PostRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.PostResponse;
import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import com.LikeMiracleTeam.MemorialBackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getRecommendPosts() {
        return postService.getRecommendPosts();
    }

    @GetMapping("/{postNo}")
    public ResponseEntity<PostResponse> getPost(@PathVariable(name = "postNo")Long postNo) {
        return postService.getPost(postNo);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestHeader(name = "Authorization")String tokenString,
                                                   @RequestBody PostRequest request) {
        return postService.createPost(tokenString, request);
    }

    @PatchMapping("/{postNo}")
    public ResponseEntity<PostResponse> updatePost(@RequestHeader(name = "Authorization")String tokenString,
                                                   @RequestBody PostRequest request,
                                                   @PathVariable(name = "postNo")Long postNo) {
        return postService.updatePost(tokenString, request, postNo);
    }

    @DeleteMapping("/{postNo}")
    public ResponseEntity<PostResponse> deletePost(@RequestHeader(name = "Authorization")String tokenString,
                                                   @PathVariable(name = "postNo")Long postNo){
        return postService.deletePost(tokenString, postNo);
    }

    @PostMapping("/{postNo}/like")
    public ResponseEntity<Void> like(@RequestHeader(name = "Authorization")String tokenString,
                                           @PathVariable(name = "postNo")Long postNo){
        return postService.like(tokenString, postNo);
    }

    @DeleteMapping("/{postNo}/like")
    public ResponseEntity<Void> cancelLike(@RequestHeader(name = "Authorization")String tokenString,
                                                   @PathVariable(name = "postNo")Long postNo){
        return postService.cancelLike(tokenString, postNo);
    }

}
