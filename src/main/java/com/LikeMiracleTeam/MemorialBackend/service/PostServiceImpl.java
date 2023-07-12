package com.LikeMiracleTeam.MemorialBackend.service;

import com.LikeMiracleTeam.MemorialBackend.dto.request.PostRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.PostResponse;
import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import com.LikeMiracleTeam.MemorialBackend.entity.User;
import com.LikeMiracleTeam.MemorialBackend.entity.UserLikePost;
import com.LikeMiracleTeam.MemorialBackend.filter.JwtProvider;
import com.LikeMiracleTeam.MemorialBackend.repository.PostRepository;
import com.LikeMiracleTeam.MemorialBackend.repository.UserLikePostRepository;
import com.LikeMiracleTeam.MemorialBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserLikePostRepository userLikePostRepository;

    //**********************************************//
    //************* crud 여기부터 *******************//
    //**********************************************//

    @Override
    public ResponseEntity<PostResponse> getPost(Long postNo) {
        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        return ResponseEntity.ok(new PostResponse(post));
    }

    @Override
    public ResponseEntity<PostResponse> createPost(String tokenString, PostRequest request) {
        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        Post post = Post.builder()
                .user(user)
                .content(request.getContent())
                .isPublic(request.getIs_public())
                .like(0)
                .build();
        postRepository.save(post);

        return ResponseEntity.ok(new PostResponse(post));
    }

    @Transactional
    @Override
    public ResponseEntity<PostResponse> updatePost(String tokenString, PostRequest request, Long postNo) {
        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();
        if (!post.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        post.update(request.getContent(), request.getIs_public());

        return ResponseEntity.ok(new PostResponse(post));
    }

    @Transactional
    public ResponseEntity<PostResponse> deletePost(String tokenString, Long postNo) {
        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();
        if (!post.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        postRepository.delete(post);

        return ResponseEntity.ok(new PostResponse(post));
    }

    //**********************************************//
    //************* crud 여기까지 *******************//
    //**********************************************//

    @Transactional
    public ResponseEntity<Void> like(String tokenString, Long postNo) {
        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();
        post.like();

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        UserLikePost userLikePost = UserLikePost.builder()
                .user(user)
                .post(post)
                .build();

        userLikePostRepository.save(userLikePost);

        return ResponseEntity.ok().build();

    }

    @Transactional
    public ResponseEntity<Void> cancelLike(String tokenString, Long postNo) {
        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();
        post.cancelLike();

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        UserLikePost userLikePost = UserLikePost.builder()
                .user(user)
                .post(post)
                .build();

        userLikePostRepository.delete(userLikePost);

        return ResponseEntity.ok().build();

    }

}
