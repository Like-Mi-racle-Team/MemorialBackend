package com.LikeMiracleTeam.MemorialBackend.repository;

import com.LikeMiracleTeam.MemorialBackend.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost_PostPk(Long postPk, Pageable pageable);

    Optional<Comment> findByContentAndCreatedAtAndModifiedAt(String content, LocalDateTime createdAt, LocalDateTime modifiedAt);

}
