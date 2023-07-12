package com.LikeMiracleTeam.MemorialBackend.service;

import com.LikeMiracleTeam.MemorialBackend.dto.request.PostRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.PostResponse;
import org.springframework.http.ResponseEntity;

public interface PostService {
     ResponseEntity<PostResponse> getPost(Long postNo);
    // ResponseEntity<PostResponse> getRecommendPosts();
     ResponseEntity<PostResponse> createPost(String tokenString, PostRequest request);
     ResponseEntity<PostResponse> updatePost(String tokenString, PostRequest request, Long postNo);
     ResponseEntity<PostResponse> deletePost(String tokenString,  Long postNo);

}
