package com.LikeMiracleTeam.MemorialBackend.dto.response;

import com.LikeMiracleTeam.MemorialBackend.entity.Post;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    private String writer_name;
    private String writer_id;
    private String content;
    private Boolean is_public;
    private Integer like;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public PostResponse(Post post) {
        this.writer_name = post.getUser().getName();
        this.writer_id = post.getUser().getUserId();
        this.content = post.getContent();
        this.is_public = post.getIsPublic();
        this.like = post.getLike();
        this.created_at = post.getCreatedAt();
        this.modified_at = post.getModifiedAt();
    }
}
