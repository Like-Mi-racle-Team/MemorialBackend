package com.LikeMiracleTeam.MemorialBackend.dto.response;

import com.LikeMiracleTeam.MemorialBackend.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {
    private Long commentNo;
    private String writer_name;
    private String writer_id;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public CommentResponse(Comment comment) {
        this.commentNo = comment.getCommentPk();
        this.writer_name = comment.getUser().getName();
        this.writer_id = comment.getUser().getUserId();
        this.content = comment.getContent();
        this.created_at = comment.getCreatedAt();
        this.modified_at = comment.getModifiedAt();
    }
}
