package com.LikeMiracleTeam.MemorialBackend.service.impl;

import com.LikeMiracleTeam.MemorialBackend.dto.request.CommentRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.CommentResponse;
import com.LikeMiracleTeam.MemorialBackend.entity.Comment;
import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import com.LikeMiracleTeam.MemorialBackend.entity.User;
import com.LikeMiracleTeam.MemorialBackend.filter.JwtProvider;
import com.LikeMiracleTeam.MemorialBackend.repository.CommentRepository;
import com.LikeMiracleTeam.MemorialBackend.repository.PostRepository;
import com.LikeMiracleTeam.MemorialBackend.repository.UserRepository;
import com.LikeMiracleTeam.MemorialBackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PostRepository postRepository;

    @Override
    public ResponseEntity<Page<CommentResponse>> getComments(Long postNo, Pageable pageable) {
         Page<Comment> commentPage = commentRepository.findByPost_PostPk(postNo, pageable);
         return ResponseEntity.ok(commentPage.map(CommentResponse::new));
    }

    @Override
    public ResponseEntity<CommentResponse> postComment(String tokenString, Long postNo, CommentRequest request) {
        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .build();
        commentRepository.save(comment);

        return ResponseEntity.ok(new CommentResponse(comment));
    }

    @Override
    @Transactional
    public ResponseEntity<CommentResponse> updateComment(String tokenString, Long commentNo, CommentRequest request) {
        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        Optional<Comment> optionalComment = commentRepository.findById(commentNo);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Comment comment = optionalComment.get();
        if (!comment.getUser().equals(user)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        comment.update(request.getContent());

        return ResponseEntity.ok(new CommentResponse(comment));
    }

    @Override
    public ResponseEntity<CommentResponse> deleteComment(String tokenString, Long commentNo) {
        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        Optional<Comment> optionalComment = commentRepository.findById(commentNo);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Comment comment = optionalComment.get();
        if (!comment.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commentRepository.delete(comment);

        return ResponseEntity.ok(new CommentResponse(comment));
    }
}
