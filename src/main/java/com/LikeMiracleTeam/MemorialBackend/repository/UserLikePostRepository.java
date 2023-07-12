package com.LikeMiracleTeam.MemorialBackend.repository;

import com.LikeMiracleTeam.MemorialBackend.entity.UserLikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikePostRepository extends JpaRepository<UserLikePost, Long> {
}
