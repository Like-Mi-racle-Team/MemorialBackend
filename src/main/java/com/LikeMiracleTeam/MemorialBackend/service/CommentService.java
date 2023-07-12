package com.LikeMiracleTeam.MemorialBackend.service;

import com.LikeMiracleTeam.MemorialBackend.dto.request.CommentRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<Page<CommentResponse>> getComments(Long postNo, Pageable pageable);
    ResponseEntity<CommentResponse> postComment(String tokenString, Long postNo, CommentRequest request);
    ResponseEntity<CommentResponse> updateComment(String tokenString, Long commentNo, CommentRequest request);
    ResponseEntity<CommentResponse> deleteComment(String tokenString, Long commentNo);
}
