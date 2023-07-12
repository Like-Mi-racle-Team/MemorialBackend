package com.LikeMiracleTeam.MemorialBackend.entity;


import com.LikeMiracleTeam.MemorialBackend.entity.primaryKey.UserLikePostId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "user_like_post")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserLikePost {
    @EmbeddedId
    private UserLikePostId userLikePostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk")
    @MapsId("userPk")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_pk")
    @MapsId("postPk")
    private Post post;
}
