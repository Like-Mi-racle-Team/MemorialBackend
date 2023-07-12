package com.LikeMiracleTeam.MemorialBackend.repository;

import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import com.LikeMiracleTeam.MemorialBackend.entity.User;
import com.LikeMiracleTeam.MemorialBackend.entity.UserLikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLikePostRepository extends JpaRepository<UserLikePost, Long> {
    Optional<UserLikePost> findByUserAndPost(User user, Post post);

    Boolean existsByUserAndPost(User user, Post post);

}
