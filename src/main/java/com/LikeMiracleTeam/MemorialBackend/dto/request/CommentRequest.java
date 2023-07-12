package com.LikeMiracleTeam.MemorialBackend.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    @ApiModelProperty(value = "댓글내용", required = true)
    private String content;
}
