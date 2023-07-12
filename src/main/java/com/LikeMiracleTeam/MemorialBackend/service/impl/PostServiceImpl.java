package com.LikeMiracleTeam.MemorialBackend.service.impl;

import com.LikeMiracleTeam.MemorialBackend.dto.request.PostRequest;
import com.LikeMiracleTeam.MemorialBackend.dto.response.PostResponse;
import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import com.LikeMiracleTeam.MemorialBackend.entity.User;
import com.LikeMiracleTeam.MemorialBackend.entity.UserLikePost;
import com.LikeMiracleTeam.MemorialBackend.entity.primaryKey.UserLikePostId;
import com.LikeMiracleTeam.MemorialBackend.filter.JwtProvider;
import com.LikeMiracleTeam.MemorialBackend.repository.PostRepository;
import com.LikeMiracleTeam.MemorialBackend.repository.UserLikePostRepository;
import com.LikeMiracleTeam.MemorialBackend.repository.UserRepository;
import com.LikeMiracleTeam.MemorialBackend.service.FileService;
import com.LikeMiracleTeam.MemorialBackend.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserLikePostRepository userLikePostRepository;
    private final FileService fileService;


    @Override
    public ResponseEntity<List<PostResponse>> getRecommendPosts() {
        Random r = new Random();
        List<Post> posts = new ArrayList<>();
        List<Post> betsPosts = getBestPosts();

        int count = Math.min(betsPosts.size(), 20);
        int a[] = new int[count];

        for(int i=0; i<count; i++) {
            a[i] = r.nextInt(Math.min(betsPosts.size(), 100));
            for (int j = 0; j < i; j++) {
                if (a[i] == a[j]) {
                    i--;
                }
            }
        }
            if (betsPosts.isEmpty()) {
               return ResponseEntity.noContent().build();
            }

        for (int i = 0; i < count; i++) {
            posts.add(betsPosts.get(a[i]));
        }

        return ResponseEntity.ok(posts.stream().map(PostResponse::new).toList());
    }

    @Cacheable(cacheNames = "bestPosts", key = "#root.target + #root.methodName + '_' + #p0")
    public List<Post> getBestPosts() {
        return postRepository.findByIsPublicTrueOrderByLikeDesc();
    }

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
    public ResponseEntity<PostResponse> createPost(String tokenString, PostRequest request, MultipartFile file) {
        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        String fileName = null;
        if (!file.isEmpty() || file!= null) {
            try {
                fileName = fileService.saveFile(file);
            } catch (IOException e) {
                return ResponseEntity.internalServerError().build();
            }
        }

        Post post = Post.builder()
                .user(user)
                .content(request.getContent())
                .isPublic(request.getIs_public())
                .fileName(fileName)
                .oriFileName(file.getName())
                .build();
        try {
            postRepository.save(post);
        } catch (Exception e1) {
            try {
                fileService.deleteFile(fileName);
            } catch (IOException e2) {
                return ResponseEntity.internalServerError().build();
            }
        }

        return ResponseEntity.ok(new PostResponse(post));
    }

    @Transactional
    @Override
    public ResponseEntity<PostResponse> updatePost(String tokenString, PostRequest request, MultipartFile file, Long postNo) {
        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();
        if (!post.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String fileName = null;
        if (!file.isEmpty() || file!= null) {
            try {
                fileService.deleteFile(post.getFileName());
                fileName = fileService.saveFile(file);
            } catch (IOException e) {
                return ResponseEntity.internalServerError().build();
            }
        }


        post.update(request.getContent(), request.getIs_public(), file.getName(), fileName);

        return ResponseEntity.ok(new PostResponse(post));
    }

    @Override
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


    @Override
    @Transactional
    public ResponseEntity<Void> like(String tokenString, Long postNo) {
        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        if(userLikePostRepository.existsByUserAndPost(user, post)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        post.like();
        UserLikePost userLikePost = UserLikePost.builder()
                .userLikePostId(new UserLikePostId(user.getUserPk(), post.getPostPk()))
                .user(user)
                .post(post)
                .build();
        userLikePostRepository.save(userLikePost);

        return ResponseEntity.ok().build();

    }

    @Override
    @Transactional
    public ResponseEntity<Void> cancelLike(String tokenString, Long postNo) {
        Optional<Post> optionalPost = postRepository.findById(postNo);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = optionalPost.get();

        User user = userRepository.findByUserId(jwtProvider.getId(tokenString)).get();

        Optional<UserLikePost> optionalUserLikePost = userLikePostRepository.findByUserAndPost(user, post);
        if (optionalUserLikePost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserLikePost userLikePost = optionalUserLikePost.get();

        post.cancelLike();
        userLikePostRepository.delete(userLikePost);

        return ResponseEntity.ok().build();

    }

}
