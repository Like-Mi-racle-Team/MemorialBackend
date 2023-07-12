package com.LikeMiracleTeam.MemorialBackend.repository;

import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    Optional<Post> findById(Long Id);
    List<Post> findByIsPublicTrueOrderByLikeDesc();

}
