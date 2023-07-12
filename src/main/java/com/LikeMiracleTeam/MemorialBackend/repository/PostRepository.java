package com.LikeMiracleTeam.MemorialBackend.repository;

import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import com.LikeMiracleTeam.MemorialBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    Optional<Post> findById(Long Id);

    Optional<Post> findByUserAndIsPublicAndCreatedAtAndModifiedAt(User user, Boolean isPublic, LocalDateTime createdAt, LocalDateTime modifiedAt);

    List<Post> findByIsPublicTrueOrderByLikeDesc();

}
