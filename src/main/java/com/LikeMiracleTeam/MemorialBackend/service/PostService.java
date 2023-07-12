package com.LikeMiracleTeam.MemorialBackend.service;

import com.LikeMiracleTeam.MemorialBackend.dto.request.PostRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
     ResponseEntity<PostResponse> getPost(Long postNo);
     ResponseEntity<List<PostResponse>> getRecommendPosts();
     ResponseEntity<PostResponse> createPost(String tokenString, PostRequest request);
     ResponseEntity<PostResponse> updatePost(String tokenString, PostRequest request, Long postNo);
     ResponseEntity<PostResponse> deletePost(String tokenString,  Long postNo);
     ResponseEntity<Void> like(String tokenString, Long postNo);
     ResponseEntity<Void> cancelLike(String tokenString, Long postNo);

}
