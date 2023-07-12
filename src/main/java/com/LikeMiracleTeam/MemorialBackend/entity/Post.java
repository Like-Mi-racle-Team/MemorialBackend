package com.LikeMiracleTeam.MemorialBackend.entity;

import com.LikeMiracleTeam.MemorialBackend.entity.baseEntity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "post")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_pk")
    private Long postPk;

    @JoinColumn(name = "user_pk")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "like")
    private Integer like;

    public void update(String content, Boolean isPublic) {
        if (content != null) {
            this.content = content;
        }
        if (isPublic != null) {
            this.isPublic = isPublic;
        }
    }
}
